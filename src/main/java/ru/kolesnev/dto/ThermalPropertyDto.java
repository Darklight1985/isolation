package ru.kolesnev.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 1, message = "Задайте плотность не ниже 1 кг/м^3")
    @Max(value = 1000, message = "Задайте плотность не выше 1000 кг/м^3")
    private Integer density;

    @NotNull(message = "Необходимо задать теплопроводность")
    @Schema(description = "Теплопроводность")
    @Min(value = 0, message = "Теплопроводность не может быть ниже 0 Вт/м^2*К")
    private Double conductivity;
}
