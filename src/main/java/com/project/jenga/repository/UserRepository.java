package com.project.jenga.repository;

import com.project.jenga.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 회원 찾기 (로그인용)
    Optional<User> findByEmail(String email);

    // 같은 이메일 중복 여부 확인 (회원가입용)
    boolean existsByEmail(String email);
}
