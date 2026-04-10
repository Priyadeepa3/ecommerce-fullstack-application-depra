package com.priya.depra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepraApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Application context should load successfully")
    void contextLoads() {
        assertNotNull(context, "Application context should not be null");
    }

    @Test
    @DisplayName("Main application class should be present in context")
    void testMainBeanExists() {
        boolean exists = context.containsBeanDefinition("depraApplication");
        assertTrue(exists, "Main Spring Boot application bean should exist");
    }

    @Test
    @DisplayName("All essential beans should load")
    void testEssentialBeansLoaded() {
        assertNotNull(context.getBean("entityManagerFactory"));
        assertNotNull(context.getBean("dataSource"));
    }
}