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
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // 🌐 PUBLIC UI ROUTES (CLEAN URLS)
                        // =========================
                        .requestMatchers("/","/home", "/products","/products-detail","/cart","/checkout","/orders","/index.html","/cart.html","/checkout.html",
                                "/Order-successful.html","/Order-Summary.html","/static/**", "/images/**", "/js/**", "/css/**","/orders.html","/address.html","checkout.html","/forgotPassword.html","/resetPassword.html","/Signup.html",
                                "/order-tracking.html","/Order-Summary","/products.html","/Signup","/address","/order-tracking","/Order-successful","/Wishlist.html",
                                "/home.html","/login.html","/products-detail.html").permitAll()

                        // =========================
                        // 📄 STATIC FILES
                        // =========================
                        .requestMatchers("/static/**", "/images/**", "/css/**", "/js/**").permitAll()

                        // =========================
                        // 🔓 AUTH APIs (PUBLIC)
                        // =========================
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/users/register"
                        ).permitAll()

                        // =========================
                        // 🛍️ PUBLIC APIs
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()

                        // =========================
                        // 🔐 USER APIs (LOGIN REQUIRED)
                        // =========================
                        .requestMatchers("/api/cart/**").authenticated()
                        .requestMatchers("/api/wishlist/**").authenticated()
                        .requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers("/api/payment/**").authenticated()
                        .requestMatchers("/api/address/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").authenticated()

                        // =========================
                        // 👑 ADMIN APIs
                        // =========================
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // =========================
                        // ❗ EVERYTHING ELSE
                        // =========================
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080")); // your frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}