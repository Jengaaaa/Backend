package com.project.jenga.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

//    @Column(nullable = false, unique = true, length = 50)
//    private String name;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
//    private String job;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(String email, String name, String password, String job) {
        this.email = email;
//        this.name = name;
        this.password = password;
//        this.job = job;
        this.createdAt = LocalDateTime.now();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
