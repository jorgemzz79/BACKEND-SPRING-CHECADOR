package com.backend.APIRest.controlller;

import com.backend.APIRest.model.dto.request.usuario.LoginRequestDto;
import com.backend.APIRest.model.dto.request.usuario.NuevoUsuarioRequestDto;
import com.backend.APIRest.model.dto.request.usuario.UsuarioEditadoRequestDto;
import com.backend.APIRest.model.dto.response.respuesta.Respuesta;
import com.backend.APIRest.model.dto.response.usuario.DatosDeUsuarioResponseDto;
import com.backend.APIRest.model.dto.response.usuario.JwtAuthenticationDto;
import com.backend.APIRest.model.dto.response.usuario.LoginResponseDto;
import com.backend.APIRest.model.entidades.checador.Usuario;
import com.backend.APIRest.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @PostMapping("/login")
    public Respuesta<LoginResponseDto> iniciarSesion(@Valid  @RequestBody LoginRequestDto loginRequestDto)
    {
        JwtAuthenticationDto jwtAuthenticationDto= usuarioService.iniciarSesion(loginRequestDto);
        Usuario usuario= usuarioService.obtenerUsuarioPorEmail(loginRequestDto.getEmail());
        return new Respuesta<> (HttpStatus.OK,new LoginResponseDto(usuario,jwtAuthenticationDto),"Acceso Correcto","OK");
    }

    @PreAuthorize("hasRole('NUEVO USUARIO')")
    @PostMapping("/nuevo")
    public Respuesta<DatosDeUsuarioResponseDto> nuevoUsuario(@Valid @RequestBody NuevoUsuarioRequestDto nuevoUsuarioRequestDto) {
        Usuario usuario = usuarioService.nuevoUsuario(nuevoUsuarioRequestDto);
        return new Respuesta<>(HttpStatus.OK, new DatosDeUsuarioResponseDto(usuario), "Usuario creado correctamente", "OK");
    }

    @PreAuthorize("hasRole('EDITAR USUARIO')")
    @PutMapping("/editar/{id}")
    public Respuesta<DatosDeUsuarioResponseDto> editarUsuario(@Valid @PathVariable Integer id, @RequestBody UsuarioEditadoRequestDto usuarioEditadoRequestDto)
    {
            Usuario usuario = usuarioService.editarUsuario(id, usuarioEditadoRequestDto);
            return new Respuesta<> (HttpStatus.OK,new DatosDeUsuarioResponseDto(usuario), "Datos de usuario actualizados exitosamente", "OK");
    }

}
