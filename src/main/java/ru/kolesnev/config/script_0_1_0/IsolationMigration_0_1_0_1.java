package ru.kolesnev.config.script_0_1_0;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.config.BaseJavaMigration;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalResistanceRepository;

import java.util.ArrayList;
import java.util.List;

//@ApplicationScoped
//@RequiredArgsConstructor
//public class IsolationMigration_0_1_0_1 extends BaseJavaMigration {
//
//    private final ThermalResistanceRepository thermalResistanceRepository;
//
//    @Override
//    protected void migrate() {
//        List <ThermalResistance> list = thermalResistanceRepository.findAll().list();
//        String forma = "(%s, %s, %s, %s)";
//        List<String> res = new ArrayList<>();
//        for (ThermalResistance thermalResistance: list) {
//            ThermalResistanceId id = thermalResistance.getResistanceId();
//            String str = forma.formatted(id.getTemperature(), id.getNominalDiameter(), thermalResistance.getResistanceValue(), id.getSurfaceType());
//            res.add(str);
//        }
//        System.out.println(res);
//    }
//}
