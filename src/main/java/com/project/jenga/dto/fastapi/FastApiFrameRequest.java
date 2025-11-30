package com.project.jenga.dto.fastapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FastApiFrameRequest {
    private String sessionId;
    private String frame;
}
