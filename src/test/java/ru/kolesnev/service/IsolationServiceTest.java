package ru.kolesnev.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.ThermalPropertyId;
import ru.kolesnev.dto.ThermalPropertyDto;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;

import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@QuarkusTest
@DisplayName("При создании эксперимента:")
public class IsolationServiceTest {

    @Inject
    IsolationService isolationService;

    @InjectMock
    IsolationRepository isolationRepository;

    @InjectMock
    ThermalPropertyRepository thermalPropertyRepository;

    private static final UUID ISOLATION_ID = UUID.randomUUID();
    private final Isolation isolation = new Isolation();

    @Nested
    class DeleteIsolationClass {

        @BeforeEach
        void init() {
            Mockito.when(isolationRepository.findById(ISOLATION_ID)).thenReturn(Optional.of(isolation));
        }

        @Test
        @DisplayName("""
                Если создание прошло успешно, эксперимент сохраняется в служебную базу.
                """)
        void test_1() {
            isolationService.deleteIsolation(ISOLATION_ID);
            Mockito.verify(thermalPropertyRepository).deleteAllByIsolation(isolation);
            Mockito.verify(isolationRepository).deleteById(ISOLATION_ID);
        }
    }

    @Nested
    class DeletePropertyClass {

        private final ThermalPropertyDto dto = new ThermalPropertyDto();
        private final Short temperature = Short.valueOf(randomNumeric(1));
        private final ThermalPropertyId thermalPropertyId = new ThermalPropertyId(isolation, temperature);

        @BeforeEach
        void init() {
            dto.setIsolation(ISOLATION_ID);
            dto.setTemperature(temperature);
            Mockito.when(isolationRepository.findById(ISOLATION_ID)).thenReturn(Optional.of(isolation));
        }

        @Test
        @DisplayName("""
                Если создание прошло успешно, эксперимент сохраняется в служебную базу.
                """)
        void test_1() {
            isolationService.deleteProperty(dto);
            Mockito.verify(thermalPropertyRepository).deleteByThermalPropertyId(Mockito.eq(thermalPropertyId));
        }
    }
}
