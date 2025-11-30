package com.project.jenga.controller;

import com.project.jenga.dto.*;
import com.project.jenga.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    // 세션 초기화
    @PostMapping("/init")
    public String init(@RequestBody InitRequest request) {
        return analysisService.initSession(request.getUserId());
    }

    // 프레임 전달
    @PostMapping("/frame")
    public String sendFrame(@RequestBody FrameRequest request) {
        analysisService.sendFrame(request.getUserId(), request.getFrame());
        return "frame ok";
    }

    // 오디오 전달
    @PostMapping("/audio")
    public String sendAudio(@RequestBody AudioRequest request) {
        analysisService.sendAudio(request.getUserId(), request.getAudio());
        return "audio ok";
    }

    // 결과 조회
    @GetMapping("/result")
    public ResultResponse getResult(@RequestParam Long userId) {
        return analysisService.getResult(userId);
    }
}
