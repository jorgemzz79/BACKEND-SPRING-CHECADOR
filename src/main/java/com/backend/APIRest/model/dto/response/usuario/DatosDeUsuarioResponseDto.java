package com.backend.APIRest.model.dto.response.usuario;

import com.backend.APIRest.model.entidades.checador.Usuario;
import lombok.Data;
@Data
public class DatosDeUsuarioResponseDto
{
    private Integer id;
    private String nombre;
    private String email;
    private String perfil;
    private Integer caja;
    private Integer cajero;

    private boolean activo;
    public DatosDeUsuarioResponseDto()
    {

    }
    public DatosDeUsuarioResponseDto(Usuario usuario)
    {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil().getNombre();
        this.activo=usuario.isActivo();
        this.caja= usuario.getCaja();
        this.cajero=usuario.getCajero();
    }
}
