package com.insert.ioj.domain.problem.domain;

import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
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
public class Problem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String inputContent;

    @Column(columnDefinition = "TEXT")
    private String outputContent;

    private int level;

    private int memoryLimit;

    private int timeLimit;

    public Problem(String title, String content, String inputContent, String outputContent, int level, int memoryLimit, int timeLimit) {
        this.title = title;
        this.content = content;
        this.inputContent = inputContent;
        this.outputContent = outputContent;
        this.level = level;
        this.memoryLimit = memoryLimit;
        this.timeLimit = timeLimit;
    }
}
