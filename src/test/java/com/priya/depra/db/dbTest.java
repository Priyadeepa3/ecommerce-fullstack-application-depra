package com.priya.depra.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbTest {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://depra-ecommerce31-priyathiru333-0b1e.e.aivencloud.com:24871/defaultdb?sslMode=REQUIRED";
        String user = "avnadmin";
        String password = System.getenv("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println("CONNECTED SUCCESSFULLY");

        conn.close();
    }
}