package ru.kolesnev.dto;


import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "ДТО, описывающее пользователя и его роль")
@Data
public class UserRoleDto {

    @Schema(description = "Имя пользователя")

    private String username;

    @Schema(description = "Роль пользователя")
    private String role;

    @Schema(description = "Токен доступа")
    String accessToken;
}
