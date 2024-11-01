package ru.kolesnev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Embeddable
public class HeatFluxId {

    @Column(name = "temperature")
    private Short temperature;

    @Column(name = "indoors")
    private boolean indoors;

    @Column(name = "long_work")
    private boolean longWork;

    @Column(name = "nominal_diameter")
    private Integer nominalDiameter;
    
}
