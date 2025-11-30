package com.project.jenga.controller;

import com.project.jenga.dto.analysis.ResultResponse;
import com.project.jenga.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // 최근 결과 조회
    @GetMapping("/latest")
    public ResultResponse getLatestResult(@RequestParam Long userId) {
        return resultService.getLatestResult(userId);
    }

    // 전체 결과 히스토리 조회
    @GetMapping("/history")
    public Object getResultHistory(@RequestParam Long userId) {
        return resultService.getResultHistory(userId);
    }
}
