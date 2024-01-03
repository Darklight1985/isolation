package ru.kolesnev.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermalPropertyDto {

    @NotBlank(message = "Необходимо задать изоляцию")
    private UUID isolation;

    @NotNull(message = "Необходимо задать температуру")
    @Schema(description = "Температура")
    private Short temperature;

    @NotNull(message = "Необходимо задать плотность")
    private Integer density;

    @NotNull(message = "Необходимо задать теплопроводность")
    private Double conductivity;
}
