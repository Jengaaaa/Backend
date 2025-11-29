package com.project.jenga.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String email;
//    private String name;
    private String password;
    private String confirmPassword;
    private String jobType;
}
