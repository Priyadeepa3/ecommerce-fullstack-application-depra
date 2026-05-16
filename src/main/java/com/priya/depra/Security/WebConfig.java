package com.priya.depra.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Map clean URLs to static HTML files using redirect.
     * DO NOT use "forward:/" with Thymeleaf removed — it causes
     * TemplateInputException and crashes the server.
     * These redirects let browser bookmarks like /home work.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/home.html");
        registry.addRedirectViewController("/home", "/home.html");
        registry.addRedirectViewController("/products", "/products.html");
        registry.addRedirectViewController("/products-detail", "/products-detail.html");
        registry.addRedirectViewController("/cart", "/cart.html");
        registry.addRedirectViewController("/checkout", "/checkout.html");
        registry.addRedirectViewController("/orders", "/orders.html");
        registry.addRedirectViewController("/address", "/address.html");
        registry.addRedirectViewController("/order-tracking", "/order-tracking.html");
        registry.addRedirectViewController("/Order-successful", "/Order-successful.html");
        registry.addRedirectViewController("/Order-Summary", "/Order-Summary.html");
        registry.addRedirectViewController("/forgot-password", "/forgotPassword.html");
        registry.addRedirectViewController("/reset-password", "/resetPassword.html");
        registry.addRedirectViewController("/Signup", "/Signup.html");
        registry.addRedirectViewController("/login", "/login.html");
    }

    /**
     * Explicitly serve static resources from /static/.
     * Spring Boot does this by default, but being explicit avoids
     * any ordering conflict with the security filter chain.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}