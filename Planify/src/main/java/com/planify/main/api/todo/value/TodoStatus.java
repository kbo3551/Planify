package com.planify.main.api.todo.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.planify.main.api.member.value.Gender;

public enum TodoStatus {
    PENDING("Pending"),
    PROGRESS("Progress"),
    COMPLETED("Completed");
	
    private final String displayName;

    TodoStatus(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return this.name();
    }

    @JsonCreator
    public static TodoStatus fromCode(Object input) {
        if (input instanceof String) {
            String code = ((String) input).trim();
            for (TodoStatus todoStatus : TodoStatus.values()) {
                if (todoStatus.name().equalsIgnoreCase(code)) {
                    return todoStatus;
                }
            }
        } else if (input instanceof TodoStatus) {
            return (TodoStatus) input;
        }
        throw new IllegalArgumentException("Invalid gender input: " + input);
    }
}