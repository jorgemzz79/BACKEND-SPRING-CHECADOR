package com.backend.APIRest.service.usuario;

import com.backend.APIRest.model.entidades.checador.Usuario;
import com.backend.APIRest.model.dto.request.usuario.LoginRequestDto;
import com.backend.APIRest.model.dto.request.usuario.NuevoUsuarioRequestDto;
import com.backend.APIRest.model.dto.request.usuario.UsuarioEditadoRequestDto;
import com.backend.APIRest.model.dto.response.usuario.JwtAuthenticationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {
    Usuario nuevoUsuario(NuevoUsuarioRequestDto request);
    Usuario editarUsuario(Integer id, UsuarioEditadoRequestDto usuarioEditadoRequestDto);
    JwtAuthenticationDto iniciarSesion(LoginRequestDto request);
    Boolean autenticarUsuario (int idUsuario, String password);
    Usuario obtenerUsuarioPorEmail(String email);
    Usuario obtenerUsuarioPorId(Integer id);

    JwtAuthenticationDto obtenerToken(Usuario usuario);
    UserDetailsService userDetailsService();
}