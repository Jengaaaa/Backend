package com.project.jenga.controller;

import com.project.jenga.dto.*;
import com.project.jenga.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1단계 회원가입
    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    // 2단계 회원가입
    @PostMapping("/signup/job")
    public String updateJob(@RequestBody JobSelectRequest request) {
        userService.updateJob(request.getUserId(), request.getJob());
        return "직군 선택 완료";
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
