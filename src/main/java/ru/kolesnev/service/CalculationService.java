package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import ru.kolesnev.domain.CalculateThicknessDto;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.ResultDto;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.enums.NominalDiameter;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.exception.CustomException;
import ru.kolesnev.repository.HeatFluxRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;
import ru.kolesnev.utils.ThermalCoefUtils;
;
import java.util.List;
import java.util.Optional;
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

        PageRequest pageRequest = PageRequest.of(0, 1);

        List<ThermalProperty> optTempMin = thermalPropertyRepository.selectPropertyForMin(innerTemp, isolationId, pageRequest);
        List<ThermalProperty> optTempMax = thermalPropertyRepository.selectPropertyForMax(innerTemp, isolationId, pageRequest);
        String thickness;
        if (optTempMax.isEmpty() || optTempMin.isEmpty()) {
            throw new CustomException();
        }
        ThermalProperty tempMin = optTempMin.get(0);
        ThermalProperty tempMax = optTempMax.get(0);
        Double conductivity;
        if (tempMin == tempMax) {
            conductivity = tempMin.getConductivity();
        } else {
          conductivity = ((tempMax.getConductivity() - tempMin.getConductivity()) / (tempMax.getThermalPropertyId().getTemperature()
                    - tempMin.getThermalPropertyId().getTemperature())) * (innerTemp
                    - tempMin.getThermalPropertyId().getTemperature()) + tempMin.getConductivity();
        }

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

        ThermalResistanceId thermalResistanceId = new ThermalResistanceId((short) 100, 100, SurfaceType.INNER_LOW_RADIATION);

         var th = thermalResistanceRepository.findByThermalId(thermalResistanceId);
        System.out.println(th.get());

        return null;
    }
}
