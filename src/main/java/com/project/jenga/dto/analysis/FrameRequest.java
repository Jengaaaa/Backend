package com.project.jenga.dto.analysis;

import lombok.Getter;

@Getter
public class FrameRequest {
    private Long userId;
    private String frame;  // base64
}
