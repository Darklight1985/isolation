package ru.kolesnev.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kolesnev.config.CustomResource;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.dto.CalculateThicknessDto;
import ru.kolesnev.dto.ConditionDto;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.repository.HeatFluxRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;
import ru.kolesnev.utils.ThermalCoefUtils;

import java.util.Optional;
import java.util.UUID;

@QuarkusTest
//QuarkusTestResource(value = CockroachDBTestResource.class, restrictToAnnotatedClass = true)
@CustomResource
@DisplayName("При расчете толщины слоя изоляции:")
public class CalculationServiceTest {

    @Inject
    CalculationService calculationService;

    @InjectMock
    ThermalCoefUtils thermalCoefUtils;
    @InjectMock
    ThermalPropertyRepository thermalPropertyRepository;
    @InjectMock
    HeatFluxRepository heatFluxRepository;
    @InjectMock
    ThermalResistanceRepository thermalResistanceRepository;

    private static final UUID ISOLATION_ID = UUID.randomUUID();
    private final CalculateThicknessDto dto = new CalculateThicknessDto();
    private final ConditionDto conditionDto = new ConditionDto();
    private final ThermalResistanceId thermalResistanceId = new ThermalResistanceId();
    private final ThermalResistance thermalResistance = new ThermalResistance();
    private static final double conductivity = 0.05;
    private final HeatFlux heatFlux = new HeatFlux();
    private static final Short OUTER_TEMP = -10;
    private static final Short INNER_TEMP = 100;
    private static final Integer DIAMETER = 100;
    private static final String RESULT_1 = "161,76";



    @BeforeEach
    void init() {
        conditionDto.setIndoors(true);
        conditionDto.setLongWork(true);
        conditionDto.setSurfaceType(SurfaceType.OPEN_LOW_WIND);
        conditionDto.setObjectType(ObjectType.FLAT_WALL);
        dto.setIsolation(ISOLATION_ID);
        dto.setInnerTemperature(INNER_TEMP);
        dto.setOuterTemperature(OUTER_TEMP);
        dto.setOuterCondition(conditionDto);
        heatFlux.setHeatFluxValue(34);
        Mockito.when(heatFluxRepository.checkVoid()).thenReturn(Optional.of(heatFlux));
        Mockito.when(thermalPropertyRepository.getConductivity(ISOLATION_ID, Integer.valueOf(INNER_TEMP)))
                .thenReturn(Optional.of(conductivity));
        Mockito.when(thermalCoefUtils.getHeatTransferCoef(ObjectType.FLAT_WALL, SurfaceType.OPEN_LOW_WIND))
                .thenReturn(26);
        Mockito.when(heatFluxRepository.getHeatFlux(true, true, - 1, 100))
                .thenReturn(34d);
    }

    @Test
    @DisplayName("""
                Если запустить расчет для случай плоской, то толщина будет расчитана верно.
                """)
    void test_1() {
        String res = calculationService.calculateThick(dto);
        Assertions.assertEquals(res, RESULT_1);
    }
}
