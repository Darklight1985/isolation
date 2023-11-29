package ru.kolesnev.domain;

import lombok.Data;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;

@Data
public class ConditionDto {

    private ObjectType objectType;
    private SurfaceType surfaceType;
}
