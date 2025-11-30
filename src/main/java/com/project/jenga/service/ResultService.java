package com.project.jenga.service;

import com.project.jenga.domain.DepressionResult;
import com.project.jenga.dto.analysis.ResultResponse;
import com.project.jenga.repository.DepressionResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final DepressionResultRepository resultRepository;

    // 최근 결과 조회
    public ResultResponse getLatestResult(Long userId) {
        DepressionResult result = resultRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RuntimeException("분석 결과가 없습니다!"));

        return new ResultResponse(result.getScore(), result.getLevel());
    }

    // 전체 히스토리 조회
    public List<DepressionResult> getResultHistory(Long userId) {
        return resultRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    // 분석 결과 저장
    public void saveResult(Long userId, double score, String level) {
        DepressionResult result = DepressionResult.builder()
                .userId(userId)
                .score(score)
                .level(level)
                .build();

        resultRepository.save(result);
    }
}
