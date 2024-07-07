package com.insert.ioj.domain.problem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    private String title;

    private String content;

    private String input;
}
