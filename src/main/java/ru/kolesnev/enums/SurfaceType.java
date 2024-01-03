package ru.kolesnev.enums;

public enum SurfaceType {

    INNER_LOW_RADIATION("С низким коэффициентом излучения"),
    INNER_HIGH_RADIATION("С высоким коэффициентом излучения");

    private final String description;

    SurfaceType(String description) {
        this.description = description;
    }
}
