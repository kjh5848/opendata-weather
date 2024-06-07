package org.example;

import lombok.RequiredArgsConstructor;
import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDao {

    // 주어진 구 이름에 해당하는 동 목록을 가져오는 SQL 쿼리
    private static final String SELECT_DONG_LIST_QUERY = "SELECT DISTINCT 3단계 FROM opendata WHERE 2단계 = ?";
    // 주어진 동 이름에 해당하는 세부 데이터를 가져오는 SQL 쿼리
    private static final String SELECT_COORDINATES_QUERY = "SELECT " +
            "격자_X as x,격자_Y as y FROM opendata WHERE 3단계 = ?";


    // 주어진 구 이름에 해당하는 동 목록을 가져오는 메서드
    public List<String> getDongList(String guName) {
        List<String> dongList = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_DONG_LIST_QUERY)) {
            statement.setString(1, guName); // 구 이름을 쿼리의 파라미터로 설정
            System.out.println("Executing query for 동 목록: " + statement); // 쿼리 실행 전 출력
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dongList.add(resultSet.getString("3단계")); // 동 이름을 리스트에 추가
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dongList;
    }


    public double[] getCoordinates(String dongName) {
        double[] coordinates = new double[2];
        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COORDINATES_QUERY)) {
            statement.setString(1, dongName);
            System.out.println("Executing query for 좌표: " + statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    coordinates[0] = resultSet.getDouble("x");
                    coordinates[1] = resultSet.getDouble("y");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
