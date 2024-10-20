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
    @CsvBindByPosition(position = 0)
    private int exampleColOne;

    @CsvBindByPosition(position = 1)
    private int exampleColTwo;

    @CsvBindByPosition(position = 2)
    private SurfaceType exampleColThree;

    @CsvBindByPosition(position = 3)
    private double exampleColFour;
}