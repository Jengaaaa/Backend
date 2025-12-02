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

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String job;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder

    public User(String name, String email, String password, String job) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.job = job;
        this.createdAt = LocalDateTime.now();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateJob(String job) {
        this.job = job;
    }
}
