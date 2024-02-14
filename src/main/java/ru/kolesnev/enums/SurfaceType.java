package ru.kolesnev.enums;

import lombok.Getter;

@Getter
public enum SurfaceType {

    INNER_LOW_RADIATION("Внутренние покрытия, с низким коэффициентом излучения"),
    INNER_HIGH_RADIATION("Внутренние покрытия, с высоким коэффициентом излучения"),
    OPEN_LOW_WIND("Поверхность на открытом воздухе, при низкой скорости ветра 5 м/с"),
    OPEN_MIDDLE_WIND("Поверхность на открытом воздухе, при средней скорости ветра 10 м/с"),
    OPEN_HIGH_WIND("Поверхность на открытом воздухе, при высокой скорости ветра 15 м/с");

    private final String description;

    SurfaceType(String description) {
        this.description = description;
    }
}
