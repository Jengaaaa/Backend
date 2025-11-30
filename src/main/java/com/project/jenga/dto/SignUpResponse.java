package com.project.jenga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {
    private Long userId;
    private String message;
}
