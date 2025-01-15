package com.planify.main.api.todo.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
    public static TodoStatus fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("TodoStatus code cannot be null or empty");
        }
        for (TodoStatus status : TodoStatus.values()) {
            if (status.name().equalsIgnoreCase(code.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TodoStatus code: " + code);
    }
}