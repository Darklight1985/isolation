package ru.kolesnev.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.kolesnev.domain.UserCreateDto;
import ru.kolesnev.dto.UserLoginDto;
import ru.kolesnev.service.UserService;

@RequiredArgsConstructor
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Контроллер для работы с пользователями")
public class UserController {

    private final UserService userService;

    @POST
    @RolesAllowed("admin")
    @Operation(description = "Задание нового админа")
    @Transactional
    public Response createIsolation(@Valid @Parameter(description = "Логин и пароль нового администратора",
            name = "UserCreateDto") UserCreateDto dto) {
        userService.addUser(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@Valid @Parameter(description = "Вход для администратора")UserLoginDto dto) {
        return userService.login(dto);
    }
}
