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

    /**
     * Величина характерной температуры при величины плотности
     */
    @Column(name = "temperature")
    private Short temperature;

    /**
     * Флаг, указывающий, что оборудование находится в помещении или на улице
     */
    @Column(name = "indoors")
    private boolean indoors;

    /**
     * Флаг указывающий, что оборудование преднзначено для работы более 5000  часов или нет
     */
    @Column(name = "long_work")
    private boolean longWork;

    /**
     * Номинальный диаметр трудопровода(В случае плоской стенки указывать - 1)
     */
    @Column(name = "nominal_diameter")
    private Integer nominalDiameter;
    
}
