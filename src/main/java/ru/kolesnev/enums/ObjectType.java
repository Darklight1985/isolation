package ru.kolesnev.enums;

import lombok.Getter;

@Getter
public enum ObjectType {
    HORIZONTAL_PIPE("Горизонтальный трубопровод"),
    VERTICAL_PIPE("Вертикальный трубопровод"),
    FLAT_WALL("Плоская стенка");

    private final String name;

    ObjectType(String name) {
        this.name = name;
    }
}
