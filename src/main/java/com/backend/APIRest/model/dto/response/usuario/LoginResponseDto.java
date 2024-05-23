package com.backend.APIRest.model.dto.response.usuario;

import com.backend.APIRest.model.entidades.checador.Usuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponseDto extends DatosDeUsuarioResponseDto {
    private String token;

    public LoginResponseDto(Usuario usuario, JwtAuthenticationDto jwtAuthenticationDto) {
        super(usuario);
        this.token = jwtAuthenticationDto.getToken();
    }
}
