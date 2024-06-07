package org.example.weather._core.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getInstance() {
        // 데이터 베이스 계정 정보
        String username = "root";
        String password = "1234";
        String dbUrl = "jdbc:mysql://localhost:3306/weatherdb";

        try {
            // 데이터 베이스 연결
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("DB 연결 성공");
            return connection;
        } catch (Exception e) {
             throw new RuntimeException(e.getMessage());
        }
    }

}
