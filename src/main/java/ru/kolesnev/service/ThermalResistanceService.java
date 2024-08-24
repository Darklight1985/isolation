package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.HeatFluxId;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.repository.ThermalResistanceRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ThermalResistanceService {

    private final ThermalResistanceRepository thermalResistanceRepository;


    public void readData(File file) {
        List<ThermalResistance> fluxes = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    List<String> datas =  Arrays.stream(scanner.nextLine().split(" "))
                            .collect(Collectors.toList());
                    Integer diameter = Integer.valueOf(datas.get(0));
                    Short temperature = Short.valueOf(datas.get(1));
                    SurfaceType surfaceType = SurfaceType.valueOf(datas.get(2));
                    Double resValue = Double.valueOf(datas.get(3));
                    ThermalResistanceId thermalResistanceId = new ThermalResistanceId(temperature, diameter, surfaceType);
                    ThermalResistance thermalResistance = new ThermalResistance(thermalResistanceId, resValue);
                    fluxes.add(thermalResistance);
                }
        } catch (FileNotFoundException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        thermalResistanceRepository.saveAll(fluxes);
    }
}
