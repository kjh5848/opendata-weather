package org.example.weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeatherDAO {

    private final Connection connection;

    public WeatherDAO(Connection connection) {
        this.connection = connection;
    }

    public List<String> 동찾기(String gu){

        List<String> dongs = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement("select 3단계 from opendata where 2단계 = ?");
            pstmt.setString(1, gu);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                dongs.add(rs.getString("3단계"));
            }
            return dongs;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String, String> 위경도찾기(String dong){
        Map<String, String> log = new HashMap<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement("select 격자_X, 격자_Y from opendata where 3단계 = ?");
            pstmt.setString(1, dong);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                log.put("격자_X", rs.getString("격자_X"));
                log.put("격자_Y", rs.getString("격자_Y"));
            }

            return log;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}