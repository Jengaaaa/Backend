package com.project.jenga.repository;

import com.project.jenga.domain.DepressionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepressionResultRepository extends JpaRepository<DepressionResult, Long> {

    List<DepressionResult> findByUserIdOrderByCreatedAtDesc(Long userId);
}
