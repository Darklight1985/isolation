package ru.kolesnev.domain;

import lombok.Getter;

public record PropertyDto (Short temperature, Double conductivity) implements Comparable<PropertyDto>{
    @Override
    public int compareTo(PropertyDto o) {
        return this.temperature() - o.temperature;
    }
}
