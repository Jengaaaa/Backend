package com.project.jenga.dto.fastapi;

import lombok.Getter;

@Getter
public class AudioRequest {
    private Long userId;
    private String audio; // base64
}
