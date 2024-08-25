package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.dto.CalculateThicknessDto;
import ru.kolesnev.dto.ConditionDto;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.exception.CustomException;
import ru.kolesnev.repository.HeatFluxRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;
import ru.kolesnev.utils.ThermalCoefUtils;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CalculationService {

    private final ThermalPropertyRepository thermalPropertyRepository;
    private final ThermalCoefUtils thermalCoefUtils;
    private final HeatFluxRepository heatFluxRepository;
    private final ThermalResistanceRepository thermalResistanceRepository;


    public String calculateThick(CalculateThicknessDto dto) {
        if (thermalResistanceRepository.checkVoid().isEmpty() || heatFluxRepository.checkVoid().isEmpty()) {
            throw new CustomException("Need to define heat flux and thermal resistance for  calculation");
        }
        return ObjectType.FLAT_WALL.equals(dto.getOuterCondition().getObjectType()) ? calculateFlatWall(dto) : calculateDiameter(dto);
    }

    private String calculateFlatWall(CalculateThicknessDto dto) {
        UUID isolationId = dto.getIsolation();
        //Проверку по айдишнику нужно, что такая изоляция есть

        short innerTemp = dto.getInnerTemperature();

        double conductivity = thermalPropertyRepository.getConductivity(isolationId, (int) innerTemp);

        int heatTransCoefOut = thermalCoefUtils.getHeatTransferCoef(dto.getOuterCondition().getObjectType(),
                dto.getOuterCondition().getSurfaceType());

        double heatFlux = heatFluxRepository.getHeatFlux(dto.getOuterCondition().isIndoors(), dto.getOuterCondition().isLongWork(),
                -1, Integer.valueOf(dto.getInnerTemperature()));

        double thick = ((dto.getInnerTemperature() - dto.getOuterTemperature()) / heatFlux - 1 / heatTransCoefOut) * conductivity;

        return String.format("%.2f", thick * 1000);
    }

    private String calculateDiameter(CalculateThicknessDto dto) {
        int diameter = dto.getDiameter();
        short temperature = dto.getInnerTemperature();

        ConditionDto outerCondition = dto.getOuterCondition();
        SurfaceType surfaceType = outerCondition.getSurfaceType();
        if (SurfaceType.OPEN_MIDDLE_WIND.equals(surfaceType) || SurfaceType.OPEN_HIGH_WIND.equals(surfaceType)) {
            surfaceType = SurfaceType.OPEN_LOW_WIND;
        }

        double conductivity = thermalPropertyRepository.getConductivity(dto.getIsolation(), (int) temperature);

        double thermalResistance = thermalResistanceRepository.getThermalResistance(temperature, diameter, surfaceType.name());
        double heatFlux = heatFluxRepository.getHeatFlux(dto.getOuterCondition().isIndoors(), dto.getOuterCondition().isLongWork(),
                -1, (int) temperature);
        double lnB = 2 * Math.PI * conductivity * (((temperature - dto.getOuterTemperature()) / heatFlux) - thermalResistance);
        double numberB = Math.exp(lnB);

        return String.format("%.2f", diameter * (numberB - 1) / 2);
    }
}
