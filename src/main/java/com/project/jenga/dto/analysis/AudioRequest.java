package com.project.jenga.dto.analysis;

import lombok.Getter;

@Getter
public class AudioRequest {
    private Long userId;
    private String audio; // base64
}
