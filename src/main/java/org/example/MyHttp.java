package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyHttp {

    private static HttpClient httpClient = HttpClient.newHttpClient();

    public static String get(String uri, String serviceKey, String baseTime, Integer nx, Integer ny) throws IOException, InterruptedException {
        // 오늘의 날짜를 YYYYMMDD 형식으로 가져오기
        LocalDate today = LocalDate.now();
        String baseDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // URL 및 쿼리 파라미터 설정
        String uriAndParams = "$uri?serviceKey=$serviceKey&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny&dataType=json&pageNo=1&numOfRows=1000"
                .replace("$uri", uri)
                .replace("$serviceKey", serviceKey)
                .replace("$baseDate", baseDate)
                .replace("$baseTime", baseTime)
                .replace("$nx", nx.toString())
                .replace("$ny", ny.toString());

        // URL 출력
        System.out.println("Request URL: " + uriAndParams);

        // HTTP GET 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriAndParams))
                .build();

        // 요청 보내기 및 응답 받기
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 출력
        System.out.println("Response: " + response.body());

        return response.body();
    }
}
