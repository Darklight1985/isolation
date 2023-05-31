package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.IsolationCreateDto;
import ru.kolesnev.domain.PropertyDto;
import ru.kolesnev.domain.PropertyViewDto;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalPropertyDto;
import ru.kolesnev.domain.ThermalPropertyId;
import ru.kolesnev.exception.CustomException;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class IsolationService {
    private final IsolationRepository isolationRepository;
    private final ThermalPropertyRepository thermalPropertyRepository;

    public PropertyViewDto getPropertys(List<UUID> ids) {
        var list = thermalPropertyRepository.findAllThermalPropertyByIsolationIDs(ids);
        var map = list.stream()
                .sorted()
                .collect(Collectors.groupingBy(s -> s.getThermalPropertyId().getIsolation().getMark()));

        var number = list.stream().map(s -> s.getThermalPropertyId()
                .getTemperature()).sorted().collect(Collectors.toCollection(TreeSet::new));
        var marks = map.keySet().stream().toList();

        List<List<Double>> conductivities = new ArrayList<>();
        for (var sets: map.entrySet()) {
            var propertyDtoList = sets.getValue().stream()
                    .map(t -> new PropertyDto(t.getThermalPropertyId().getTemperature(), t.getConductivity()))
                    .collect(Collectors.toMap(t -> t.temperature(), t -> t));

            for (short sh: number) {
                if (!propertyDtoList.containsKey(sh)) {
                    propertyDtoList.put(sh, new PropertyDto(sh, null));
                }
            }

            var ourList =  propertyDtoList.entrySet().stream().map(s -> s.getValue()).sorted()
                    .map(s -> s.conductivity()).toList();

            conductivities.add(ourList);
        }

        var temperatures = number.stream().toList();
        return PropertyViewDto.builder()
                .marks(marks)
                .temperatures(temperatures)
                .conductivities(conductivities)
                .build();
    }

    public void createProperty(ThermalPropertyDto dto) {
        Isolation isolation = isolationRepository.findById(dto.getIsolation()).orElseThrow();
        ThermalProperty thermalProperty = ThermalProperty.builder()
                .conductivity(dto.getConductivity())
                .density(dto.getDensity())
                .thermalPropertyId(new ThermalPropertyId(isolation, dto.getTemperature()))
                .build();
        thermalPropertyRepository.save(thermalProperty);
    }

    public void createIsolation(IsolationCreateDto dto) {
        if (isolationRepository.existsByMark(dto.getMark())) {
            throw new CustomException("Данная марка уже присутствует в базе");
        }
        Isolation isolation = Isolation.builder()
                .mark(dto.getMark())
//                .thermalProperties(new ArrayList<>())
                .build();
        isolationRepository.save(isolation);
    }

    public List<PropertyDto> getProperty(UUID id) {
        var list = thermalPropertyRepository.findAllThermalPropertyByIsolationID(id);
        return list.stream().sorted()
                .map(t -> new PropertyDto(t.getThermalPropertyId().getTemperature(), t.getConductivity())).toList();
    }

    public List<Isolation> getIsolations() {
        return isolationRepository.findAll();
    }
}
