package com.project.jenga.controller;

import com.project.jenga.dto.LoginRequest;
import com.project.jenga.dto.SignUpRequest;
import com.project.jenga.dto.LoginResponse;
import com.project.jenga.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        return "회원가입 완료!";
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
