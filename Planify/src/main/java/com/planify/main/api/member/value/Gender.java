package com.planify.main.api.member.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    M("MALE"),
    W("FEMALE");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    // DB에 저장 or 로직 내부에서 사용할 값 (ex]M, W)
    public String getCode() {
        return this.name();
    }

    // 표출 데이터 값 (ex]MALE, FEMALE)
    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    // JSON에서 Enum 변환 시 사용
    @JsonCreator
    public static Gender fromCode(Object input) {
        if (input instanceof String) {
            String code = ((String) input).trim();
            for (Gender gender : Gender.values()) {
                if (gender.name().equalsIgnoreCase(code)) {
                    return gender;
                }
            }
        } else if (input instanceof Gender) {
            return (Gender) input;
        }
        throw new IllegalArgumentException("Invalid gender input: " + input);
    }
}