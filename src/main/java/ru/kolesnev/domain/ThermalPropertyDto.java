package ru.kolesnev.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermalPropertyDto {

    private UUID isolation;

    private Short temperature;

    private Integer density;

    private Double conductivity;
}
