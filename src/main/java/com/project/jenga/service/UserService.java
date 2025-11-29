package com.project.jenga.service;

import com.project.jenga.dto.LoginRequest;
import com.project.jenga.dto.LoginResponse;
import com.project.jenga.dto.SignUpRequest;
import com.project.jenga.domain.User;
import com.project.jenga.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    public void signUp(SignUpRequest request) {

        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다!");
        }

        // 비밀번호 재확인 체크
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다!");
        }

        // jobType 유효성 검사
        if (!request.getJob().equals("firefighter") &&
                !request.getJob().equals("police")) {
            throw new RuntimeException("job이 잘못되었습니다! (firefighter / police)");
        }

        // 비밀번호 암호화 후 저장
        User user = User.builder()
                .email(request.getEmail())
//                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .job(request.getJob())
                .build();

        userRepository.save(user);
    }

    // 로그인
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다!");
        }

        // JWT 적용 전 ⇒ 임시 토큰 반환
        String fakeToken = "TOKEN_" + user.getId();

        return new LoginResponse(fakeToken);
    }
}
