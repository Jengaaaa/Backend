package com.project.jenga.service;

import com.project.jenga.domain.DepressionResult;
import com.project.jenga.repository.DepressionResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepressionResultService {

    private final DepressionResultRepository depressionResultRepository;

    public List<DepressionResult> getHistory(Long userId) {
        return depressionResultRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }
}
