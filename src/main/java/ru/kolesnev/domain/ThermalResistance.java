package ru.kolesnev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "thermal_resistance")
@Entity
public class ThermalResistance {

    @EmbeddedId
    private ThermalResistanceId resistanceId;

    @Column(name = "resistance_value")
    private Double resistanceValue;
}
