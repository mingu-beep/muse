package min.project.muse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class LastApiService {

    @Value("${last.fm.api.key}")
    private String apiKey;

    public String get(String track, int page) {

        // webClient 기본 설정
        WebClient webClient = WebClient.builder()
                .baseUrl(String.format(
                        "http://ws.audioscrobbler.com/2.0/",
                        apiKey))
                .build();

        //?method=track.search&track=Believe&api_key=%s&format=json
        // api 요청
        return webClient.get()
                .uri(uriBuilder ->
                                uriBuilder.queryParam("method", "track.search")
                                        .queryParam("track", track)
                                        .queryParam("api_key", apiKey)
                                        .queryParam("format", "json")
//                                        .queryParam("artist", "")
                                        .queryParam("limit", "10")
                                        .queryParam("page", Integer.toString(page))
                                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
