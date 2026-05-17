package com.priya.depra.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Allow all preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Static pages
                        .requestMatchers(
                                "/", "/home", "/products", "/products-detail", "/cart",
                                "/checkout", "/orders", "/address", "/order-tracking",
                                "/Order-successful", "/Order-Summary", "/forgot-password",
                                "/reset-password", "/Signup", "/login"
                        ).permitAll()
                        .requestMatchers(
                                "/*.html", "/static/**", "/images/**", "/css/**", "/js/**"
                        ).permitAll()

                        // Public API
                        .requestMatchers("/api/auth/**", "/api/users/register", "/api/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()

                        // Protected API
                        .requestMatchers("/api/cart/**").authenticated()
                        .requestMatchers("/api/wishlist/**").authenticated()
                        .requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers("/api/payment/**").authenticated()
                        .requestMatchers("/api/address/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Single CORS configuration source.
     * DELETE any separate CorsConfig.java — having two CORS beans causes conflicts.
     *
     * Uses setAllowedOriginPatterns() (not setAllowedOrigins()) so that
     * wildcard subdomains like *.vercel.app are matched correctly.
     *
     * allowCredentials is false because this app uses JWT in the Authorization
     * header, not HttpOnly cookies. Setting it true with wildcard origins
     * violates the CORS spec and breaks browsers.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "https://depra-ecom.onrender.com",
                "https://ecommerce-fullstack-application-depra-p4kuy49b5.vercel.app",   // update to your prod Vercel domain
                "https://*.vercel.app",             // all preview deployments
                "http://localhost:3000",
                "http://localhost:8080",
                "http://127.0.0.1:5500",
                "http://127.0.0.1:3000"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

        config.setAllowedHeaders(List.of(
                "Authorization", "Content-Type", "Accept",
                "Origin", "X-Requested-With", "Cache-Control", "Pragma"
        ));

        config.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
