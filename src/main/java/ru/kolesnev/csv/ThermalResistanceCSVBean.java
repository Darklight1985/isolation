package ru.kolesnev.csv;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.repository.ThermalResistanceRepository;

import java.util.List;


@Data
@ApplicationScoped
@Slf4j
public class ThermalResistanceCSVBean extends CSVBean {

    private String fileName = "csv/thermal_resistance.csv";
    private final ThermalResistanceRepository repository;

    @CsvBindByPosition(position = 0)
    private int exampleColOne;

    @CsvBindByPosition(position = 1)
    private int exampleColTwo;

    @CsvBindByPosition(position = 2)
    private SurfaceType exampleColThree;

    @CsvBindByPosition(position = 3)
    private double exampleColFour;


    @Override
    public void run()  {
        List<ThermalResistanceCSVBean> list = null;
        try {
            list = simplePositionBeanExample();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<ThermalResistance> thermalResistanceList = list.stream()
                        .map(el -> ThermalResistance.builder()
                                .resistanceValue(el.getExampleColFour())
                                .resistanceId(new ThermalResistanceId((short) el.exampleColOne, el.exampleColTwo, el.exampleColThree))
                                .build()).toList();
        repository.saveAll(thermalResistanceList);
    }
}