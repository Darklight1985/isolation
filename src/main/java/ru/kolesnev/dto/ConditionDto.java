package ru.kolesnev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;

@Data
public class ConditionDto {

    @JsonProperty("object_type")
    @NotNull(message = "Необходимо задать тип объекта")
    private ObjectType objectType;
    @JsonProperty("surface_type")
    @NotNull(message = "Необходимо задать тип поверхности")
    private SurfaceType surfaceType;
    private boolean indoors;
    @JsonProperty("long_work")
    private boolean longWork;
}
