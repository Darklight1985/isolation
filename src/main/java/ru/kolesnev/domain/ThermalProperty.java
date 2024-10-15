package ru.kolesnev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Table(name = "thermal_property")
@Entity
public class ThermalProperty implements Comparable<ThermalProperty> {

    @EmbeddedId
    private ThermalPropertyId thermalPropertyId;

    @Column(name = "density")
    private Integer density;

    @Column(name = "conductivity")
    private Double conductivity;

    @Override
    public int compareTo(ThermalProperty o) {
        return this.thermalPropertyId.getTemperature() - o.thermalPropertyId.getTemperature();
    }
}
