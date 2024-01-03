package ru.kolesnev.utils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;

import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor
public class ThermalCoefUtils {

    private Map<ObjectType, Map<SurfaceType, Integer>> heatTransferCoef;

    @PostConstruct
    private void init() {
//        heatTransferCoef = Map.of(ObjectType.HORIZONTAL_PIPE, Map.of(SurfaceType.INNER_LOW_RADIATION, 7,
//                SurfaceType.INNER_HIGH_RADIATION , 10, SurfaceType.OPEN_LOW_WIND, 20, SurfaceType.OPEN_MIDDLE_WIND, 26,
//                SurfaceType.OPEN_HIGH_WIND, 35),
//                ObjectType.VERTICAL_PIPE, Map.of(SurfaceType.INNER_LOW_RADIATION, 8,
//                        SurfaceType.INNER_HIGH_RADIATION , 12, SurfaceType.OPEN_LOW_WIND, 26, SurfaceType.OPEN_MIDDLE_WIND, 35,
//                        SurfaceType.OPEN_HIGH_WIND, 52),
//                ObjectType.FLAT_WALL, Map.of(SurfaceType.INNER_LOW_RADIATION, 8,
//                        SurfaceType.INNER_HIGH_RADIATION , 12, SurfaceType.OPEN_LOW_WIND, 26, SurfaceType.OPEN_MIDDLE_WIND, 35,
//                        SurfaceType.OPEN_HIGH_WIND, 52));
    }

    public Integer getHeatTransferCoef(ObjectType type, SurfaceType surfaceType) {
        return heatTransferCoef.get(type).get(surfaceType);
    }
}
