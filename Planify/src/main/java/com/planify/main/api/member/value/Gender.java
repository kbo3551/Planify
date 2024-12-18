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

	public String getCode() {
        return this.name();
    }
	
	@JsonValue
    public String getDisplayName() {
        return displayName;
	}

    @JsonCreator
    public static Gender fromCode(String code) {
        for (Gender gender : Gender.values()) {
        	if (gender.name().equalsIgnoreCase(code.trim())) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender code: " + code);
    }
}