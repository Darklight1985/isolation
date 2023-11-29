package ru.kolesnev.enums;

public enum ObjectType {
    HORIZONTAL_PIPE("Горизонтальный трубопровод"),
    VERTICAL_PIPE("Вертикальный трубопровод"),
    FLAT_WALL("Плоская стенка");

    private final String name;

    ObjectType(String name) {
        this.name = name;
    }
}
