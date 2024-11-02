package ru.kolesnev.csv;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.repository.ThermalResistanceRepository;

import java.util.List;


@Data
@ApplicationScoped
@Slf4j
public class ThermalResistanceCSVBeanLoader extends CSVBeanLoader {

    private final String fileName = "deployments/app/isolation-1.0-SNAPSHOT.jar/csv/thermal_resistance.csv";
    private final ThermalResistanceRepository repository;

    @Override
    public Class getClazz() {
        return ThermalResistanceCSVBean.class;
    }

    @Override
    public void run()  {
        List<ThermalResistanceCSVBean> list;
        try {
            list = simplePositionBeanExample();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<ThermalResistance> thermalResistanceList = list.stream()
                .map(el -> (ThermalResistanceCSVBean) el)
                        .map(el -> ThermalResistance.builder()
                                .resistanceValue(el.getExampleColFour())
                                .resistanceId(new ThermalResistanceId((short) el.exampleColOne, el.exampleColTwo, el.exampleColThree))
                                .build()).toList();
        repository.saveAll(thermalResistanceList);
    }
}