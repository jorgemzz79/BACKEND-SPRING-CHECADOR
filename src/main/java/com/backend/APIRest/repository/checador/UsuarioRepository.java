package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
{
    Optional<Usuario> findByEmail(String email);
    Usuario findByNombre(String nombre);
}
