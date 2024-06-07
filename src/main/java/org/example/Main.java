package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SERVICE_KEY = "op2eVrMEmtxPEzRcxSDm8KAsZESS0JEgdxudyNSTfqkmYyqcqaHEc%2F05gBkt6J%2BgfdIdbnHtzH9fMpAsYOUGjw%3D%3D";
    private static final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // 1. 구를 입력받습니다.
        System.out.println("구를 입력하세요. [예, 종로구, 수영구]");
        String guInput = scanner.nextLine().trim();
        System.out.println("areaName = " + guInput);

        // 데이터베이스에서 해당 구의 동 목록을 조회합니다.
        AreaDao areaDao = new AreaDao();
        List<String> dongList = areaDao.getDongList(guInput);

        // 2. 동 목록을 출력합니다.
        if (dongList.isEmpty()) {
            System.err.println("해당 구에 대한 동 목록이 존재하지 않습니다.");
            return;
        } else {
            System.out.println(guInput + "의 동 목록:");
            for (String dong : dongList) {
                System.out.println(dong);
            }
        }

        // 3. 동을 입력받습니다.
        System.out.println("동을 입력하세요. [예, " + String.join(", ", dongList) + "]");
        String dongInput = scanner.nextLine().trim();
        System.out.println("dongName = " + dongInput);

        // 데이터베이스에서 해당 동의 위도와 경도를 조회합니다.
        double[] coordinates = areaDao.getCoordinates(dongInput);

        // ap
        System.out.println("시간을 입력하세요 : [예] 2시 : 1400, 3시 : 1500 ");
        String baseTime  = scanner.nextLine().trim();
        System.out.println("baseTime = " + baseTime);

        // 4. 위도와 경도를 출력합니다.
        if (coordinates[0] == 0.0 && coordinates[1] == 0.0) {
            System.err.println("해당 동에 대한 좌표가 존재하지 않습니다.");
        } else {
            System.out.println(dongInput + "의 좌표:");
            System.out.println("격자_x :" + coordinates[0]);
            System.out.println("격자_y :" + coordinates[1]);

            // 5. API 요청
            String response = MyHttp.get(API_URL, SERVICE_KEY, baseTime, (int) Math.round(coordinates[0]), (int) Math.round(coordinates[1]));

            // 6. JSON 파싱 및 현재 온도 출력
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            JsonObject responseObj = jsonObject.getAsJsonObject("response");
            JsonObject bodyObj = responseObj.getAsJsonObject("body");
            JsonObject itemsObj = bodyObj.getAsJsonObject("items");
            JsonArray itemArray = itemsObj.getAsJsonArray("item");

            for (JsonElement element : itemArray) {
                WeatherDto weather = gson.fromJson(element, WeatherDto.class);
                if ("T1H".equals(weather.getCategory())) {
                    System.out.println("현재 온도: " + weather.getObsrValue() + "°C");
                    break;
                }
            }
        }
    }
}
