package com.priya.depra.Admin.Controller;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ROOT CAUSE ANALYSIS — Render Cold Starts
 *
 * WHY LOGIN "FAILS MOST OF THE TIME" (Intermittent "Failed to Fetch"):
 *
 *   Render's free tier spins down services after 15 minutes of inactivity.
 *   On the next request, Render must:
 *     1. Allocate a container (~5-10 seconds)
 *     2. Start the JVM (~5-10 seconds)
 *     3. Spring Boot context initialization (~10-15 seconds)
 *     4. HikariCP connects to Aiven MySQL with SSL (~3-5 seconds)
 *     5. Hibernate schema validation/update (~5-15 seconds)
 *   TOTAL: 28-55 seconds before the first request can be served.
 *
 *   Browser fetch() calls have a DEFAULT TIMEOUT that varies:
 *     - Chrome: no explicit timeout but OS network stack typically cuts off at ~120s
 *     - The frontend code has no explicit timeout configured
 *     - BUT: if the user closes the tab or navigates away during cold start, the
 *       request is cancelled → "Failed to Fetch" (AbortError)
 *
 *   More critically: even with no tab navigation, a 30-50 second delay in the
 *   login response causes users to click "Login" multiple times (creating duplicate
 *   DB hits), or to assume the app is broken and try again — creating a storm of
 *   requests against a still-warming instance.
 *
 * SOLUTIONS:
 *
 *   SERVER SIDE (this file):
 *     Add a /api/health endpoint that:
 *       a) Pings the database to confirm HikariCP is connected
 *       b) Returns 200 with detailed pool stats
 *       c) Can be called by monitoring tools or frontend to pre-warm the service
 *
 *   CLIENT SIDE (see COLD_START_WARMUP.js in the fix package):
 *     The frontend should:
 *       a) Call /api/health on page load with a short timeout
 *       b) Show a "Connecting..." spinner if health check takes > 2s
 *       c) Only enable the login button after the health check succeeds
 *       d) Add retry logic with exponential backoff for cold-start scenarios
 *
 *   INFRASTRUCTURE (Render dashboard):
 *     Option A (Free): Use UptimeRobot (free) to ping /api/health every 14 minutes
 *                      This prevents the service from sleeping entirely.
 *                      URL to monitor: https://depra-ecom.onrender.com/api/health
 *     Option B (Paid): Upgrade to Render's Starter plan ($7/month) → no sleep
 *
 * AIVEN CONNECTION POOL HEALTH:
 *   This endpoint also exposes HikariCP pool statistics, which helps diagnose
 *   the "Cannot acquire connection" errors from BUG #4.
 */
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthcheckController {

    private final DataSource dataSource;

    /**
     * Health check endpoint — permitted without authentication in SecurityConfig.
     *
     * Returns:
     *   200 OK  — service is up, database is reachable
     *   503     — service is up but database connection failed
     *
     * Monitor this URL with UptimeRobot (free) to prevent Render cold starts:
     *   https://depra-ecom.onrender.com/api/health
     *   Check interval: 14 minutes (Render sleeps after 15 min inactivity)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("service", "DEPRA E-commerce");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("status", "UP");

        // Test database connectivity
        try (Connection conn = dataSource.getConnection()) {
            boolean valid = conn.isValid(5); // 5 second timeout
            status.put("database", valid ? "CONNECTED" : "UNREACHABLE");

            // Expose HikariCP pool statistics for diagnostics
            if (dataSource instanceof HikariDataSource hikariDataSource) {
                HikariPoolMXBean poolStats = hikariDataSource.getHikariPoolMXBean();
                if (poolStats != null) {
                    Map<String, Object> pool = new LinkedHashMap<>();
                    pool.put("active", poolStats.getActiveConnections());
                    pool.put("idle", poolStats.getIdleConnections());
                    pool.put("waiting", poolStats.getThreadsAwaitingConnection());
                    pool.put("total", poolStats.getTotalConnections());
                    status.put("connectionPool", pool);
                }
            }

            if (!valid) {
                status.put("status", "DEGRADED");
                return ResponseEntity.status(503).body(status);
            }

        } catch (Exception e) {
            status.put("database", "ERROR");
            status.put("databaseError", e.getMessage());
            status.put("status", "DEGRADED");
            return ResponseEntity.status(503).body(status);
        }

        return ResponseEntity.ok(status);
    }
}
