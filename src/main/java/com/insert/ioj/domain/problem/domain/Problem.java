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

    private String title;

    private String content;

    private int level;

    private int memoryLimit;

    private int timeLimit;

    public Problem(String title, String content, int level, int memoryLimit, int timeLimit) {
        this.title = title;
        this.content = content;
        this.level = level;
        this.memoryLimit = memoryLimit;
        this.timeLimit = timeLimit;
    }
}
