package ru.kolesnev.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "ДТО для задания тепловых свойств материала")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermalPropertyDto extends ThermalPropertyDeleteDto{

    @NotNull(message = "Необходимо задать плотность")
    @Schema(description = "Плотность")
    private Integer density;

    @NotNull(message = "Необходимо задать теплопроводность")
    @Schema(description = "Теплопроводность")
    private Double conductivity;
}
