package com.backend.APIRest.model.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequestDto
{
    @NotNull
    @Email
    @Size(min = 4, max = 100, message = " debe tener entre 4 y 100 caracteres")
    private String email;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = " debe tener entre 4 y 20 caracteres alfanuméricos, sin espacios ni caracteres especiales ")
    private String password;
}
