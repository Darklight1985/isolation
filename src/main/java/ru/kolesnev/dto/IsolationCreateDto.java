package ru.kolesnev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsolationCreateDto {

    @NotBlank(message="Mark may not be blank")
    @Size(min = 3, max = 20, message = "Наименование должно быть от 3 до 20 символов")
    private String mark;
}
