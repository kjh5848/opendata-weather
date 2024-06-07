package org.example.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final DatabaseConfig config;

    public DatabaseConnection() {
        this.config = new DatabaseConfig();
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("MySQL JDBC 드라이버를 로드할 수 없습니다.");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
    }
}
