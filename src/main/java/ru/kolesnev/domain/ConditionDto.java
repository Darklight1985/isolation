package ru.kolesnev.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.enums.WindType;

@Data
public class ConditionDto {

    @JsonProperty("object_type")
    private ObjectType objectType;
    @JsonProperty("surface_type")
    private SurfaceType surfaceType;
    @JsonProperty("wind_type")
    private WindType windType;
    private boolean indoors;
    @JsonProperty("long_work")
    private boolean longWork;
}
