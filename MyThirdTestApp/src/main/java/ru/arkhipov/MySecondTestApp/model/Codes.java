package ru.arkhipov.MySecondTestApp.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Codes {
    SUCCESS("success"),
    FAILED("Ошибка валидации");

    private final String name;
    Codes(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
