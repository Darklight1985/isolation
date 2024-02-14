package ru.kolesnev.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;

@Data
public class ConditionDto {

    @JsonProperty("object_type")
    private ObjectType objectType;
    @JsonProperty("surface_type")
    private SurfaceType surfaceType;
    private boolean indoors;
    @JsonProperty("long_work")
    private boolean longWork;
}
