package ru.kolesnev.dto;

public record PropertyDto (Short temperature, Double conductivity, Integer density) implements Comparable<PropertyDto>{
    @Override
    public int compareTo(PropertyDto o) {
        return this.temperature() - o.temperature;
    }
}
