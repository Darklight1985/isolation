package ru.kolesnev.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateThicknessDto {

    @Schema(description = "Наружная температура")
    private Short outerTemperature;

    @Schema(description = "Внутренняя температура")
    private Short innerTemperature;

    @Schema(description = "Наружный диаметра трубопровода")
    private Integer diameter;

    @Schema(description = "Материал теплозиоляции")
    private UUID isolation;

    @Schema(description = "Условия со стороны наружной поверхности")
    private ConditionDto outerCondition;
}
