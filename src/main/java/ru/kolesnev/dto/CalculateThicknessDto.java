package ru.kolesnev.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.kolesnev.annotations.Diameter;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateThicknessDto {

    @Schema(description = "Наружная температура")
    @Min(value = -50, message = "Задайте температуру выше -50 градусов по цельсию")
    @Max(value = 50, message = "Задайте температуру ниже -50 градусов по цельсию")
    private Short outerTemperature;

    @Schema(description = "Внутренняя температура")
    @Min(value = -50, message = "Задайте температуру выше -50 градусов по цельсию")
    @Max(value = 300, message = "Задайте температуру ниже 300 градусов по цельсию")
    private Short innerTemperature;

    @Schema(description = "Условный проход трубопровода")
    @NotNull(message = "Необходимо задать диаметр трубопровода")
    @Diameter
    private Integer diameter;

    @Schema(description = "Материал теплозиоляции")
    @NotNull(message = "Необходимо задать изоляцию")
    private UUID isolation;

    @Schema(description = "Условия со стороны наружной поверхности")
    private ConditionDto outerCondition;
}
