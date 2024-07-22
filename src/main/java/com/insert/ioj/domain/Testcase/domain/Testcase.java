package com.insert.ioj.domain.Testcase.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Testcase {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String input;

    private String output;

    public Testcase(String input, String output) {
        this.input = input;
        this.output = output;
    }
}
