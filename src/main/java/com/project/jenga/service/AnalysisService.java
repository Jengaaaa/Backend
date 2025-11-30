package com.project.jenga.service;

import com.project.jenga.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final WebClient webClient; // WebClientConfig에서 생성

    private final Map<Long, String> sessionMap = new ConcurrentHashMap<>();

    private static final String FASTAPI_URL = "http://fusion:8000";

    // 1. 세션 초기화
    public String initSession(Long userId) {

        FastApiInitRequest req = new FastApiInitRequest(userId);

        FastApiInitResponse response = webClient.post()
                .uri(FASTAPI_URL + "/init")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(FastApiInitResponse.class)
                .block();

        // FastAPI에서 받은 session_id 저장
        sessionMap.put(userId, response.getSessionId());

        return response.getSessionId();
    }

    // 2. 프레임 전송
    public void sendFrame(Long userId, String base64Frame) {

        String sessionId = getSessionId(userId);

        FastApiFrameRequest req = new FastApiFrameRequest(sessionId, base64Frame);

        webClient.post()
                .uri(FASTAPI_URL + "/frame")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 3. 오디오 전송
    public void sendAudio(Long userId, String base64Audio) {

        String sessionId = getSessionId(userId);

        FastApiAudioRequest req = new FastApiAudioRequest(sessionId, base64Audio);

        webClient.post()
                .uri(FASTAPI_URL + "/audio")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 4. 결과 조회
    public ResultResponse getResult(Long userId) {

        String sessionId = getSessionId(userId);

        FastApiResultResponse response = webClient.get()
                .uri(FASTAPI_URL + "/result?session_id=" + sessionId)
                .retrieve()
                .bodyToMono(FastApiResultResponse.class)
                .block();

        // DB 저장은 여기서 수행 (원하면 저장 코드 추가)
        return new ResultResponse(response.getScore(), response.getLevel());
    }

    // sessionId 가져오기
    private String getSessionId(Long userId) {
        if (!sessionMap.containsKey(userId)) {
            throw new RuntimeException("세션이 초기화되지 않았습니다. /init 먼저 호출해주세요!");
        }
        return sessionMap.get(userId);
    }
}
