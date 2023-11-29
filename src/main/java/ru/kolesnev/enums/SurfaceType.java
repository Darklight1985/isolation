package ru.kolesnev.enums;

public enum SurfaceType {

    INNER_LOW_RADIATION("В помещении с низким коэффициентом излучения"),
    INNER_HIGH_RADIATION("В помещении, с высоким коэффициентом излучения"),
    OPEN_LOW_WIND("На открытом воздухе, при скорости ветра 5 м/с"),
    OPEN_MIDDLE_WIND("На открытом воздухе, при скорости ветра 10 м/с"),
    OPEN_HIGH_WIND("На открытом воздухе, при скорости ветра 15 м/с");

    private final String description;

    SurfaceType(String description) {
        this.description = description;
    }
}
