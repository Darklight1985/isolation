package ru.kolesnev.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;

import java.util.Optional;
import java.util.UUID;

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
