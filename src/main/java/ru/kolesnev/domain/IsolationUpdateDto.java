package ru.kolesnev.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsolationUpdateDto {

    @NotBlank(message = "Необходимо задать изоляцию")
    private UUID isolation;

    @NotBlank(message="Mark may not be blank")
    private String mark;
}
