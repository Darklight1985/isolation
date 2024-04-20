package ru.kolesnev.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolesnev.domain.User;
import ru.kolesnev.domain.UserCreateDto;

@ApplicationScoped
@Slf4j
public class UserService {

    @Transactional
    public void addUser(UserCreateDto dto) {
        User.deleteAll();
        User.add(dto.getLogin(), dto.getPassword(), "admin");
    }
}
