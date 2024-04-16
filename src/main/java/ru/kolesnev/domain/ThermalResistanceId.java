package ru.kolesnev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.kolesnev.enums.SurfaceType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Embeddable
public class ThermalResistanceId {

    @Column(name = "temperature")
    private Short temperature;

    @Column(name = "nominal_diameter")
    private Integer nominalDiameter;

    @Enumerated(EnumType.STRING)
    @Column(name = "surface_type")
    private SurfaceType surfaceType;
}
