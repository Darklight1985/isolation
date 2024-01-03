package ru.kolesnev.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.HeatFluxId;
import ru.kolesnev.repository.HeatFluxRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class HeatFluxService {

    private final HeatFluxRepository heatFluxRepository;

    public void readData(File file, boolean indoors, boolean longWork) {

        List<HeatFluxId> fluxIds = new ArrayList<>();
        List<HeatFlux> fluxes = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                List<Short> temps = Arrays.stream(data.split(" "))
                        .map(Short::valueOf)
                        .collect(Collectors.toList());
                for(Short temp: temps) {
                    HeatFluxId heatFluxId = new HeatFluxId();
                    heatFluxId.setTemperature(temp);
                    fluxIds.add(heatFluxId);
                }

                while (scanner.hasNextLine()) {
                    List<Short> datas =  Arrays.stream(scanner.nextLine().split(" "))
                            .map(Short::valueOf)
                            .collect(Collectors.toList());
                    Integer diameter = Integer.valueOf(datas.get(0));
                    for (int i = 0; i < fluxIds.size(); i++) {
                        Integer heatFluxValue = Integer.valueOf(datas.get(i + 1));
                        HeatFluxId heatFluxId = new HeatFluxId();
                        heatFluxId.setNominalDiameter(diameter);
                        heatFluxId.setTemperature(fluxIds.get(i).getTemperature());
                        heatFluxId.setLongWork(longWork);
                        heatFluxId.setIndoors(indoors);
                        HeatFlux heatFlux = new HeatFlux();
                        heatFlux.setHeatFluxId(heatFluxId);
                        heatFlux.setHeatFluxValue(heatFluxValue);
                        fluxes.add(heatFlux);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        heatFluxRepository.saveAll(fluxes);
    }
}
