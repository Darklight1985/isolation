package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.domain.CalculateThicknessDto;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.exception.CustomException;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;
import ru.kolesnev.utils.ThermalCoefUtils;
;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CalculationService {
    private final IsolationRepository isolationRepository;
    private final ThermalPropertyRepository thermalPropertyRepository;
    private final ThermalCoefUtils thermalCoefUtils;

    public String getPropertys(CalculateThicknessDto dto) {
        UUID isolationId = dto.getIsolation();

        var list = thermalPropertyRepository.findAllThermalPropertyByIsolationID(isolationId);
        ThermalProperty tempMin = null;
        ThermalProperty tempMax = null;
        String thickness = null;
        for (ThermalProperty property : list) {
            if (property.getThermalPropertyId().getTemperature() < dto.getInnerTemperature()) {
                tempMin = property;
            } else {
                tempMax = property;
                Double conductivity = ((tempMax.getConductivity() - tempMin.getConductivity()) / (tempMax.getThermalPropertyId().getTemperature()
                        - tempMin.getThermalPropertyId().getTemperature())) * (dto.getInnerTemperature()
                        - tempMin.getThermalPropertyId().getTemperature()) + tempMin.getConductivity();

                Integer heatTransCoefOut = thermalCoefUtils.getHeatTransferCoef(dto.getOuterCondition().getObjectType(),
                        dto.getOuterCondition().getSurfaceType());

     //           Double thermalResist =
                thickness = String.format("%.2f", ((dto.getInnerTemperature() - dto.getOuterTemperature()) * conductivity));
                break;
            }
        }
        if (thickness == null) {
            throw new CustomException("Изолируемая температура находится за гранью диапазона температурных свойств материаал изоляции");
        }
        return thickness;
    }
}
