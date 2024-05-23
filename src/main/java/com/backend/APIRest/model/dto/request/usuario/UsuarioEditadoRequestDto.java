package com.backend.APIRest.model.dto.request.usuario;

import com.backend.APIRest.model.entidades.checador.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioEditadoRequestDto {

    @Null
    @Pattern(regexp = "^[a-zA-Z0-9\\\\s]{10,100}$", message = " debe tener entre 10 y 100 caracteres alfanuméricos, sin caracteres especiales")
    private String nombre;

    @Null
    @Email
    @Size(min = 4, max = 100, message = " debe tener entre 4 y 100 caracteres")
    private String email;

    @Null
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = " debe tener entre 4 y 10 caracteres alfanuméricos, sin caracteres especiales")
    private String password;

    @Column(name = "caja" , unique=true)
    Integer caja;

    @Column(name = "cajero", unique=false)
    Integer cajero;
    @Null
    @Pattern(regexp = "^[a-zA-Z0-9]{4,50}$", message = " debe tener entre 4 y 50 caracteres alfanuméricos, sin caracteres especiales")
    private String perfil;

    @Null
    private Boolean activo;

    public UsuarioEditadoRequestDto() {

    }
    public UsuarioEditadoRequestDto(Usuario usuario) {
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil().getNombre();
        this.password=usuario.getPassword();
        this.activo=usuario.isActivo();
    }
}
