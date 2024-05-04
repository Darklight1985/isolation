package ru.kolesnev.dto;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Builder
@Data
@Schema(description = "Набор свойств марок телоизоляции в зависимости от температуры")
public class PropertyViewDto {
    @Schema(description = "Список марок теплоизоляции")
    List<String> marks;
    @Schema(description = "Упорядоченный список температур")
    List<Short> temperatures;
    @Schema(description = "Упорядоченный список теплопроводности")
    List<List<Double>> conductivities;
    @Schema(description = "Упорядоченный список плотности")
    List<List<Integer>> densities;
}
