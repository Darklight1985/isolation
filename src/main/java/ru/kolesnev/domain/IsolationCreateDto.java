package ru.kolesnev.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsolationCreateDto {

    @NotBlank(message="Mark may not be blank")
    private String mark;
}
