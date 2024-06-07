package org.example.database;


import lombok.Data;

@Data
public class DatabaseConfig {
    private String url;
    private String user;
    private String password;

    // 기본 생성자
    public DatabaseConfig() {
        // 기본값 설정 (예: 환경 변수나 시스템 프로퍼티로부터 가져올 수도 있음)
        this.url = "jdbc:mysql://localhost:3306/weatherdb";
        this.user = "root";
        this.password = "1234";
    }

    // 매개변수 있는 생성자
    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
}
