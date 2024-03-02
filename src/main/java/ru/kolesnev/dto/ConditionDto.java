package ru.kolesnev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;

@Schema(description = "ДТО, описывающее условия для расчета")
@Data
public class ConditionDto {

    @Schema(description = "Тип объекта")
    @JsonProperty("object_type")
    @NotNull(message = "Необходимо задать тип объекта")
    private ObjectType objectType;
    @Schema(description = "Тип поверхности")
    @JsonProperty("surface_type")
    @NotNull(message = "Необходимо задать тип поверхности")
    private SurfaceType surfaceType;
    @Schema(description = "Флан внутри помещения ли находится объект")
    private boolean indoors;
    @Schema(description = "Флаг, что объект в условиях доолгой работы")
    @JsonProperty("long_work")
    private boolean longWork;
}
