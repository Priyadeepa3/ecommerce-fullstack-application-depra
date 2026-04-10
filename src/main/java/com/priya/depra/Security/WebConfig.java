package com.priya.depra.Security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Home & static pages
        registry.addViewController("/").setViewName("forward:/home.html");
        registry.addViewController("/home").setViewName("forward:/home.html");
        registry.addViewController("/products").setViewName("forward:/products.html");
        registry.addViewController("/products-detail").setViewName("forward:/products-detail.html");
        registry.addViewController("/cart").setViewName("forward:/cart.html");
        registry.addViewController("/checkout").setViewName("forward:/checkout.html");
        registry.addViewController("/forgot-password").setViewName("forward:/forgotPassword.html");
        registry.addViewController("/reset-password").setViewName("forward:/resetPassword.html");

        // ✅ FIXED: orders page (list of user orders)
        registry.addViewController("/orders").setViewName("forward:/orders.html");

        // Address page
        registry.addViewController("/address").setViewName("forward:/address.html");

        // Order tracking
        registry.addViewController("/order-tracking").setViewName("forward:/order-tracking.html");

        // Order success (keep as is)
        registry.addViewController("/Order-successful").setViewName("forward:/Order-successful.html");

        // Order success (keep as is)
        registry.addViewController("/Order-Summary").setViewName("forward:/Order-Summary.html");
    }
}