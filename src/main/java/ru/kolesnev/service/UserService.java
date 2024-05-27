package ru.kolesnev.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.domain.User;
import ru.kolesnev.domain.UserCreateDto;
import ru.kolesnev.dto.UserLoginDto;
import org.apache.commons.codec.binary.Base64;

@ApplicationScoped
@Slf4j
public class UserService {

    private final String format = "%s:%s";

    @Transactional
    public void addUser(UserCreateDto dto) {
        User.deleteAll();
        User.add(dto.getLogin(), dto.getPassword(), "admin");
    }

    public String login(UserLoginDto dto) {
        if (User.existUser(dto)) {
            var bytes = Base64.encodeBase64(format.formatted(dto.getUsername(), dto.getPassword()).getBytes());
            var data = new String(bytes);
            return data;
        }
        throw new ForbiddenException("username or password not correct");
    }
}
