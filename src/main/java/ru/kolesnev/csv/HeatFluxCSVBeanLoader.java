package ru.kolesnev.csv;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.resource.spi.ConfigProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.HeatFluxId;
import ru.kolesnev.repository.HeatFluxRepository;

import java.util.List;

@Data
@ApplicationScoped
@Slf4j
public class HeatFluxCSVBeanLoader extends CSVBeanLoader {

    @ConfigProperty(defaultValue = "csv-location")
    private final String csvLocation;
    private final String fileName = csvLocation + "csv/thermal_flux.csv";
    private final HeatFluxRepository repository;

    @Override
    public Class getClazz() {
        return HeatFluxCSVBean.class;
    }

    @Override
    public void run()  {
        List<HeatFluxCSVBean> list;
        try {
            list = simplePositionBeanExample();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<HeatFlux> thermalResistanceList = list.stream()
                        .map(el -> HeatFlux.builder()
                                .heatFluxValue(el.exampleColThree)
                                .heatFluxId(new HeatFluxId((short) el.exampleColTwo, el.exampleColFour, el.exampleColFive, el.exampleColOne))
                                .build()).toList();
        repository.saveAll(thermalResistanceList);
    }
}