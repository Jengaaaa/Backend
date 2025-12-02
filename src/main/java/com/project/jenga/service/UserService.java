package com.project.jenga.service;

import com.project.jenga.domain.User;
import com.project.jenga.dto.user.LoginRequest;
import com.project.jenga.dto.user.LoginResponse;
import com.project.jenga.dto.user.SignUpRequest;
import com.project.jenga.dto.user.SignUpResponse;
import com.project.jenga.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 1단계 회원가입
    public SignUpResponse signUp(SignUpRequest request) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다!");
        }

        // 비밀번호 확인 체크
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다!");
        }

        // 비밀번호 암호화
        String encoded = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoded)
                .job(null)
                .build();

        User saved = userRepository.save(user);

        // 프론트에서 2단계 진행할 수 있도록 userId 반환
        return new SignUpResponse(
                saved.getId(),
                "기본 회원가입이 완료되었습니다. 직군을 선택해주세요!"
        );
    }


    // 2단계 회원가입
    public void updateJob(Long userId, String job) {

        // job 유효성 검사
        if (!job.equals("firefighter") && !job.equals("police")) {
            throw new RuntimeException("직군이 잘못되었습니다! (firefighter / police)");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다!"));

        // User 엔티티 업데이트
        user.updateJob(job);

        userRepository.save(user);
    }


    // 로그인
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다!");
        }

        // JWT 적용 전이라 임시 토큰 발급
        String fakeToken = "TOKEN_" + user.getId();

        return new LoginResponse(fakeToken);
    }
}
