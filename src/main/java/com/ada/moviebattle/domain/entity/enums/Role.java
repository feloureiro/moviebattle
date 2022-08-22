package com.ada.moviebattle.domain.entity.enums;

public enum Role {
    ROLE_USER("ROLE_USER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
