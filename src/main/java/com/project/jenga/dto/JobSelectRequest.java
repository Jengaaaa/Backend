package com.project.jenga.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSelectRequest {
    private Long userId;
    private String job;
}
