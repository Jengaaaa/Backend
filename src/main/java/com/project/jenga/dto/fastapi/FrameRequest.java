package com.project.jenga.dto.fastapi;

import lombok.Getter;

@Getter
public class FrameRequest {
    private Long userId;
    private String frame;  // base64
}
