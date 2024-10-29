package ru.kolesnev.csv;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.repository.HeatFluxRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;

import java.util.List;

@Data
@ApplicationScoped
@Slf4j
public class HeatFluxCSVBean extends CSVBean {

    private final String fileName = "csv/heat_flux.csv";

    @CsvBindByPosition(position = 0)
    protected int exampleColOne;

    @CsvBindByPosition(position = 1)
    protected int exampleColTwo;

    @CsvBindByPosition(position = 2)
    protected int exampleColThree;

    @CsvBindByPosition(position = 3)
    protected boolean exampleColFour;

    @CsvBindByPosition(position = 4)
    protected boolean exampleColFive;
}