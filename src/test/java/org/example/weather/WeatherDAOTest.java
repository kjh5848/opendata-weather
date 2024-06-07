package org.example.weather;


import org.example.weather.dao.WeatherDAO;
import org.junit.jupiter.api.Test;
import org.example.weather._core.db.DatabaseConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDAOTest {

    @Test
    public void 동찾기_test(){
        // given
        String gu = "부산진구";

        // when
        WeatherDAO dao = new WeatherDAO(DatabaseConnection.getInstance());
        List<String> dongs = dao.동찾기(gu);

        dongs.forEach(System.out::println);
    }

    @Test
    public void 위경도찾기_test(){
        // given
        String dong = "부전제1동";

        // when
        WeatherDAO dao = new WeatherDAO(DatabaseConnection.getInstance());
        Map<String, String> log = dao.위경도찾기(dong);
        System.out.printf("", log.get("격자_X"));
    }
}
