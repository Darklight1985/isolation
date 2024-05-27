package ru.kolesnev.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "ДТО, описывающее условия для расчета")
@Data
public class UserLoginDto {

    @Schema(description = "Имя пользователя")
    @NotBlank
    private String username;
    @Schema(description = "Пароль пользователя")
    @NotBlank
    private String password;
}
