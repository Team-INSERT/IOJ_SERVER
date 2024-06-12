package com.insert.ioj.domain.user.domain;

import com.insert.ioj.domain.user.domain.type.Color;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Color color;

    public User(
        String email, String nickname, Color color
    ) {
        this.email = email;
        this.nickname = nickname;
        this.color = color;
    }
}
