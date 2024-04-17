package ru.kolesnev.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "ДТО для задания тепловых свойств материала")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermalPropertyDeleteDto {

    @NotNull(message = "Необходимо задать изоляцию")
    @Schema(description = "Идентификатор марки тепловой изоляции")
    private UUID isolation;

    @NotNull(message = "Необходимо задать температуру")
    @Schema(description = "Температура")
    private Short temperature;
}
