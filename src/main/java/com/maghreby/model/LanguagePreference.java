package com.maghreby.model;

public enum LanguagePreference {
    ENGLISH("en"),
    FRENCH("fr"),
    ARABIC("ar");

    private final String code;

    LanguagePreference(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}