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
import java.util.Map;
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
                .getTemperature()).sorted().distinct().collect(Collectors.toList());
        var iter = number.listIterator();

        var marks = map.keySet().stream().toList();

        List<List<Double>> conductivities = new ArrayList<>();
        List<List<Integer>> densities = new ArrayList<>();
        for (var sets: map.entrySet()) {
            Map<Short, PropertyDto> shortPropertyDtoMap = sets.getValue().stream()
                    .map(t -> new PropertyDto(t.getThermalPropertyId().getTemperature(), t.getConductivity(), t.getDensity()))
                    .collect(Collectors.toMap(PropertyDto::temperature, t -> t));

            for (short sh: number) {
                if (!shortPropertyDtoMap.containsKey(sh)) {
                    shortPropertyDtoMap.put(sh, new PropertyDto(sh, null, null));
                }
            }

            var conductivityList =  shortPropertyDtoMap.values().stream().sorted()
                    .map(PropertyDto::conductivity).toList();
            var densityList = shortPropertyDtoMap.values().stream().sorted()
                    .map(PropertyDto::density).toList();

            conductivities.add(conductivityList);
            densities.add(densityList);
        }

        var temperatures = number.stream().toList();
        return PropertyViewDto.builder()
                .marks(marks)
                .temperatures(temperatures)
                .conductivities(conductivities)
                .densities(densities)
                .build();
    }

    public void createProperty(ThermalPropertyDto dto) {
        Isolation isolation = isolationRepository.findById(dto.getIsolation())
                .orElseThrow(() -> new CustomException("Марка изоляции не найдена"));
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
                .map(t -> new PropertyDto(t.getThermalPropertyId().getTemperature(), t.getConductivity(),
                        t.getDensity())).toList();
    }

    public List<Isolation> getIsolations() {
        return isolationRepository.findAll();
    }
}
