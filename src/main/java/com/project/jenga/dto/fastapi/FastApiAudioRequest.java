package com.project.jenga.dto.fastapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FastApiAudioRequest {
    private String sessionId;
    private String audio;
}
