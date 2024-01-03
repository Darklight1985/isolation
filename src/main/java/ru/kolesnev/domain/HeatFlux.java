package ru.kolesnev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "heat_flux")
@Entity
public class HeatFlux {

    @EmbeddedId
    private HeatFluxId heatFluxId;

    @Column(name = "heat_flux_value")
    private Integer heatFluxValue;
}
