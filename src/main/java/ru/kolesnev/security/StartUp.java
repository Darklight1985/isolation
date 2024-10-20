package ru.kolesnev.security;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import ru.kolesnev.csv.ThermalResistanceCSVBean;
import ru.kolesnev.domain.User;

@Singleton
public class StartUp {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) throws Exception {
        if (!User.checkAdmin()) {
            User.add("admin", "admin", "admin");
        }

        ThermalResistanceCSVBean thermalResistanceCSVBean = new ThermalResistanceCSVBean();
        var list = thermalResistanceCSVBean.simplePositionBeanExample();
        System.out.println(list);
    }
}
