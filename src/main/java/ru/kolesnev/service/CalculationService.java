package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.domain.CalculateThicknessDto;
import ru.kolesnev.enums.NominalDiameter;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.exception.CustomException;
import ru.kolesnev.repository.HeatFluxRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;
import ru.kolesnev.utils.ThermalCoefUtils;
;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CalculationService {

    private final ThermalPropertyRepository thermalPropertyRepository;
    private final ThermalCoefUtils thermalCoefUtils;
    private final HeatFluxRepository heatFluxRepository;
    private final ThermalResistanceRepository thermalResistanceRepository;

    public String calculateThick(CalculateThicknessDto dto) {
        UUID isolationId = dto.getIsolation();
        Integer diameter = dto.getDiameter();

        if (!NominalDiameter.checkDiameter(diameter)) {
            throw new CustomException();
        }

        Short innerTemp = dto.getInnerTemperature();

        String thickness;
          Double conductivity = thermalPropertyRepository.getConductivity(isolationId, Integer.valueOf(innerTemp));

        Integer heatTransCoefOut = thermalCoefUtils.getHeatTransferCoef(dto.getOuterCondition().getObjectType(),
                dto.getOuterCondition().getSurfaceType());

        Double hf = heatFluxRepository.getHeatFlux(dto.getOuterCondition().isIndoors(), dto.getOuterCondition().isLongWork(),
                -1, Integer.valueOf(dto.getInnerTemperature()));

        var thick = ((dto.getInnerTemperature() - dto.getOuterTemperature()) / hf - 1 / heatTransCoefOut) * conductivity;


        thickness = String.format("%.2f", thick);
        if (thickness == null) {
            throw new CustomException("Изолируемая температура находится за гранью диапазона температурных свойств материаал изоляции");
        }
        return thickness;
    }

    public String calculateDiameter(CalculateThicknessDto dto) {
        Integer diameter = dto.getDiameter();
        Short temperature = dto.getInnerTemperature();
         var outerCondition = dto.getOuterCondition();
         SurfaceType surfaceType = outerCondition.getSurfaceType();
         if (SurfaceType.OPEN_MIDDLE_WIND.equals(surfaceType) || SurfaceType.OPEN_HIGH_WIND.equals(surfaceType)) {
             surfaceType = SurfaceType.OPEN_LOW_WIND;
         }

         var th = thermalResistanceRepository.getThermalResistance(temperature, diameter, surfaceType.name());
         Double hf = heatFluxRepository.getHeatFlux(dto.getOuterCondition().isIndoors(), dto.getOuterCondition().isLongWork(),
                -1, Integer.valueOf(temperature));
         Double lnB = 2 * 3.14;

        return null;
    }
}
