package com.project.jenga.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String passwordCheck;
}
