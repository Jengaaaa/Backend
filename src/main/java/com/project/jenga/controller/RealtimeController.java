package com.project.jenga.controller;

import com.project.jenga.config.FastApiConfig;
import com.project.jenga.domain.DepressionResult;
import com.project.jenga.repository.DepressionResultRepository;
import com.project.jenga.service.DepressionResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/realtime")
@RequiredArgsConstructor
public class RealtimeController {

    private final RestTemplate restTemplate;
    private final DepressionResultRepository depressionResultRepository;
    private final DepressionResultService depressionResultService;

    // 1) 세션 초기화
    @PostMapping("/init")
    public Map<String, Object> init(@RequestParam String userId) {
        String url = FastApiConfig.FASTAPI_BASE_URL + "/realtime/init?user_id=" + userId;
        return restTemplate.postForObject(url, null, Map.class);
    }

    // 2) 프레임 전송
    @PostMapping("/frame")
    public Map<String, Object> sendFrame(
            @RequestParam String userId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        String url = FastApiConfig.FASTAPI_BASE_URL + "/realtime/frame";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("user_id", userId);
        body.add("file", new MultipartInputStreamFileResource(
                file.getInputStream(), file.getOriginalFilename()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, request, Map.class);
    }

    // 3) 오디오 chunk 전송
    @PostMapping("/audio")
    public Map<String, Object> sendAudio(
            @RequestParam String userId,
            @RequestParam("audio") MultipartFile audio
    ) throws Exception {

        String url = FastApiConfig.FASTAPI_BASE_URL + "/realtime/audio";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("user_id", userId);
        body.add("audio_chunk", new MultipartInputStreamFileResource(
                audio.getInputStream(), audio.getOriginalFilename()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, request, Map.class);
    }

    // 4) 결과 조회 + DB 저장
    @GetMapping("/result")
    public Map<String, Object> getResult(@RequestParam String userId) {

        String url = FastApiConfig.FASTAPI_BASE_URL + "/realtime/result?user_id=" + userId;

        Map<String, Object> result = restTemplate.getForObject(url, Map.class);

        // FastAPI가 아직 결과 못 만들었을 때
        if (result == null || result.get("score") == null) {
            return result;
        }

        // score 변환 (Object → Double)
        Object scoreObj = result.get("score");
        Double score = null;

        if (scoreObj != null) {
            score = Double.parseDouble(scoreObj.toString());
        }

        // level 변환 (Object → String)
        Object levelObj = result.get("level");
        String level = (levelObj != null) ? levelObj.toString() : null;

        // DB 저장
        DepressionResult entity = DepressionResult.builder()
                .userId(Long.parseLong(userId))
                .score(score)
                .level(level)
                .createdAt(LocalDateTime.now())
                .build();

        depressionResultRepository.save(entity);

        return result;
    }

    @GetMapping("/history")
    public List<DepressionResult> getHistory(@RequestParam Long userId) {
        return depressionResultService.getHistory(userId);
    }

}
