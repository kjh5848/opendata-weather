package org.example.weather;

import com.google.gson.Gson;
import org.example.weather._core.respomse.WeatherDTO;
import org.example.weather._core.data.WeatherVO;
import org.example.weather._core.db.DatabaseConnection;
import org.example.weather._core.util.MyHttp;
import org.example.weather.dao.WeatherDAO;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("1. 구를 입력하세요.");
        System.out.println("[종로구, 종구, 부산진구]");
        Scanner sc = new Scanner(System.in);
        WeatherDAO dao = new WeatherDAO(DatabaseConnection.getInstance());

        String gu = sc.nextLine();
        List<String> dongs = dao.동찾기(gu);

        System.out.println("2. 동을 입력하세요.");
        dongs.forEach(s -> System.out.println(s));

        String dong = sc.nextLine();
        Map<String, String> los = dao.위경도찾기(dong);

        WeatherVO vo = new WeatherVO("20240607","1600",los.get("격자_X"), los.get("격자_Y"));
        try {
            String responseBody = MyHttp.get(
                    vo.uri,
                    vo.serviceKey,
                    vo.baseDate,
                    vo.baseTime,
                    vo.nx,
                    vo.ny
            );

            Gson gson = new Gson();
            WeatherDTO dto = gson.fromJson(responseBody, WeatherDTO.class);

            System.out.println("현재 온도 : "+dto.response.body.items.item.get(3).obsrValue);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }





    }
}