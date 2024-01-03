package ru.kolesnev.enums;

public enum WindType {

    OPEN_LOW_WIND("При скорости ветра 5 м/с"),
    OPEN_MIDDLE_WIND("При скорости ветра 10 м/с"),
    OPEN_HIGH_WIND("При скорости ветра 15 м/с");

    private final String description;

    WindType(String description) {
        this.description = description;
    }
}
