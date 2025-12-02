package com.project.jenga.repository;

import com.project.jenga.domain.DepressionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DepressionResultRepository extends JpaRepository<DepressionResult, Long> {

    // 최신 결과 조회
    Optional<DepressionResult> findTopByUserIdOrderByCreatedAtDesc(Long userId);
    // 히스토리 전체 조회
    List<DepressionResult> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
