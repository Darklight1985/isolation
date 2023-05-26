package ru.kolesnev.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "thermal_property")
@Entity
public class ThermalProperty implements Comparable<ThermalProperty>{

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
