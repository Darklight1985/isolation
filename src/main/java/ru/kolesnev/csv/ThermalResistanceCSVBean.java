package ru.kolesnev.csv;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.enums.SurfaceType;


@Data
@ApplicationScoped
@Slf4j
public class ThermalResistanceCSVBean extends CSVBean {

    private final String fileName = "csv/thermal_resistance.csv";

    @CsvBindByPosition(position = 0)
    protected int exampleColOne;

    @CsvBindByPosition(position = 1)
    protected int exampleColTwo;

    @CsvBindByPosition(position = 2)
    protected SurfaceType exampleColThree;

    @CsvBindByPosition(position = 3)
    protected double exampleColFour;
}