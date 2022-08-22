package com.ada.moviebattle.domain.entity.enums;

public enum Status {
    CLOSED("CLOSED"),
    OPEN("OPEN");

    private final String text;

    Status(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
