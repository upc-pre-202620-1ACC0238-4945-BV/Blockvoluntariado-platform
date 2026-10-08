package com.bv.platform.volunteers.domain.model.valueobjects;

public record PersonalName(String firstName, String lastName) {
    public String getFullName() {
        return "%s %s".formatted(firstName, lastName).trim();
    }
}
