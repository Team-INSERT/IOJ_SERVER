package com.insert.ioj.domain.user.domain;

import com.insert.ioj.domain.user.domain.type.Authority;
import com.insert.ioj.domain.user.domain.type.Color;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(max = 32)
    @Column(unique = true, length = 32)
    private String email;

    @Size(max = 20)
    @Column(length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public User(
        String email, String nickname, Color color, Authority authority
    ) {
        this.email = email;
        this.nickname = nickname;
        this.color = color;
        this.authority = authority;
    }
}
