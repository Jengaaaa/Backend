package com.project.jenga.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultResponse {
    private double score;
    private String level;
}
