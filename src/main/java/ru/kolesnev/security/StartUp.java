package ru.kolesnev.security;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import ru.kolesnev.domain.User;

@Singleton
public class StartUp {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        if (!User.checkAdmin()) {
            User.add("admin", "admin", "admin");
        }
    }
}
