package ru.kolesnev.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import ru.kolesnev.domain.User;
import ru.kolesnev.domain.UserCreateDto;
import ru.kolesnev.dto.UserLoginDto;
import ru.kolesnev.dto.UserRoleDto;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
@Slf4j
public class UserService {

    private final String format = "%s:%s";

    @ConfigProperty(name = "token.expires.in")
    long tokenExpiresIn;

    @Transactional
    public void addUser(UserCreateDto dto) {
        User.deleteAll();
        User.add(dto.getLogin(), dto.getPassword(), "admin");
    }

    public UserRoleDto login(UserLoginDto dto) {
        User user = User.existUser(dto);
        if (user == null) {
            throw new ForbiddenException("username or password not correct");
        }
        String role = user.role;
        String accessToken = Jwt.issuer("https://example.com/issuer")
                .upn(user.username)
                .groups(new HashSet<>(Arrays.asList(role)))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .issuedAt(Instant.now())
                .expiresIn(tokenExpiresIn)
                .sign();
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setAccessToken(accessToken);
        userRoleDto.setUsername(dto.getUsername());
        userRoleDto.setRole(user.role);
        return userRoleDto;
    }
}
