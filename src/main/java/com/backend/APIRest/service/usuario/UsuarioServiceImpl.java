package com.backend.APIRest.service.usuario;

import com.backend.APIRest.model.dto.request.usuario.LoginRequestDto;
import com.backend.APIRest.model.dto.request.usuario.NuevoUsuarioRequestDto;
import com.backend.APIRest.model.dto.request.usuario.UsuarioEditadoRequestDto;
import com.backend.APIRest.model.dto.response.usuario.JwtAuthenticationDto;
import com.backend.APIRest.model.entidades.checador.Perfil;
import com.backend.APIRest.model.entidades.checador.Usuario;
import com.backend.APIRest.repository.checador.PerfilRepository;
import com.backend.APIRest.repository.checador.UsuarioRepository;
import com.backend.APIRest.service.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Boolean autenticarUsuario(int idUsuario, String password) {
        Usuario usuario= obtenerUsuarioPorId(idUsuario);
        if(usuario==null)
            return false;
        try
        {
            JwtAuthenticationDto token = iniciarSesion(new LoginRequestDto(usuario.getEmail(),password));
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    @Override
    public Usuario nuevoUsuario(NuevoUsuarioRequestDto nuevoUsuarioRequestDto)
    {
        Perfil perfil =new Perfil();

        Usuario existente= obtenerUsuarioPorEmail(nuevoUsuarioRequestDto.getEmail());
        if(existente!=null)
               throw  new IllegalArgumentException("Ya existe un usuario registrado con el mismo email");

        if(nuevoUsuarioRequestDto.getPerfil()!=null)
            if(!nuevoUsuarioRequestDto.getPerfil().isEmpty())
            {
                perfil = perfilRepository.findById(nuevoUsuarioRequestDto.getPerfil())
                        .<IllegalArgumentException>orElseThrow(() -> new IllegalArgumentException("El perfil Asignado NO Existe"));

            }
        Usuario usuario = Usuario.builder().nombre(nuevoUsuarioRequestDto.getNombre()).email(nuevoUsuarioRequestDto.getEmail()).password(passwordEncoder.encode(nuevoUsuarioRequestDto.getPassword())).caja(nuevoUsuarioRequestDto.getCaja()).cajero(nuevoUsuarioRequestDto.getCajero()).perfil(perfil).activo(true).build();
        return usuarioRepository.save(usuario);
    }
    private boolean emailEnUso(Integer idUsuario, String emailNuevo)
    {
        if(emailNuevo!=null)
            if (!emailNuevo.isEmpty())
            {
                Usuario emailEnUso = usuarioRepository.findByEmail(emailNuevo).orElse(null);
                if (emailEnUso != null)
                    return !emailEnUso.getId().equals(idUsuario);
            }
       return false;
    }

    private void actualizarUsuario(Usuario usuarioExistente, UsuarioEditadoRequestDto usuarioEditadoRequestDto) {
        // Lógica de actualización aquí
        System.out.println("password1:"+usuarioExistente.getPassword());
        if (usuarioEditadoRequestDto.getNombre() != null && !usuarioEditadoRequestDto.getNombre().isEmpty()) {
            usuarioExistente.setNombre(usuarioEditadoRequestDto.getNombre());
        }

        if(usuarioEditadoRequestDto.getEmail()!=null && !usuarioEditadoRequestDto.getEmail().isEmpty())
                usuarioExistente.setEmail(usuarioEditadoRequestDto.getEmail());

        if(usuarioEditadoRequestDto.getPassword()!=null && !usuarioEditadoRequestDto.getPassword().isEmpty())
                usuarioExistente.setPassword(passwordEncoder.encode(usuarioEditadoRequestDto.getPassword()));

        if(usuarioEditadoRequestDto.getPerfil()!=null && !usuarioEditadoRequestDto.getPerfil().isEmpty())
            {
                usuarioExistente.setPerfil(perfilRepository.findById(usuarioEditadoRequestDto.getPerfil())
                        .<IllegalArgumentException>orElseThrow(() -> new IllegalArgumentException("El perfil Asignado NO Existe")));
            }

        if(usuarioEditadoRequestDto.getActivo()!=null)
            usuarioExistente.setActivo(usuarioEditadoRequestDto.getActivo());

        System.out.println("password2:"+usuarioExistente.getPassword());
    }

    @Transactional
    @Override
    public Usuario editarUsuario(Integer id, UsuarioEditadoRequestDto usuarioEditadoRequestDto) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);
        if (usuarioExistente == null)
            throw new EntityNotFoundException("No existe el Usuario con el id: " + id);

        if (emailEnUso(id, usuarioEditadoRequestDto.getEmail()))
            throw new IllegalArgumentException("El email ya se encuentra en uso");

        actualizarUsuario(usuarioExistente, usuarioEditadoRequestDto);
        System.out.println("password3:"+usuarioExistente.getPassword());
        return usuarioRepository.saveAndFlush(usuarioExistente);
    }

    @Override
    public JwtAuthenticationDto iniciarSesion(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        var user = usuarioRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationDto.builder().token(jwt).build();
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        // Buscar un usuario por email en la base de datos
        Usuario usuario=usuarioRepository.findByEmail(email).orElse(null);
        if( usuario != null)
            Hibernate.initialize(usuario.getPerfil().getPrivilegios());
        return usuario;
    }
    @Override
    public Usuario obtenerUsuarioPorId(Integer id){
        // Buscar un usuario por email en la base de datos
        return usuarioRepository.findById(id).orElse(null);
    }
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                Usuario usuario= usuarioRepository.findByEmail(username) .orElseThrow(() -> new UsernameNotFoundException("No se han logrado validar las credenciales"));

                return usuario;
               }
        };
    }

    @Override
    public JwtAuthenticationDto obtenerToken(Usuario usuario) {
        var jwt = jwtService.generateToken(usuario);
        return JwtAuthenticationDto.builder().token(jwt).build();
    }
}