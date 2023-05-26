package ru.kolesnev.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PropertyViewDto {
    List<String> marks;

    List<Short> temperatures;
    List<List<Double>> conductivities;
}
