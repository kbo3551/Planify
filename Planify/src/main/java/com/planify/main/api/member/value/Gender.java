package com.planify.main.api.member.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    M("MALE"),
    W("FEMALE"),
    U("UNKNOWN"); // UNKNOWN 성별 추가

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    // DB에 저장하거나 로직 내부에서 사용할 값 (M, W)
    public String getCode() {
        return this.name();
    }

    // 화면에 출력할 값 (MALE, FEMALE)
    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    // JSON -> Enum 변환
    @JsonCreator
    public static Gender fromDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender display name cannot be null or empty");
        }

        for (Gender gender : Gender.values()) {
            if (gender.displayName.equalsIgnoreCase(displayName.trim())) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid gender display name: " + displayName);
    }

    // DB 코드(M/W) -> Enum 변환
    public static Gender fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender code cannot be null or empty");
        }

        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(code.trim())) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid gender code: " + code);
    }
}
