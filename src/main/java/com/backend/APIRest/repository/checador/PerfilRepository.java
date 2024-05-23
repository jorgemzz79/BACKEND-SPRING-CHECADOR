package com.backend.APIRest.repository.checador;


import com.backend.APIRest.model.entidades.checador.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String>
{


}
