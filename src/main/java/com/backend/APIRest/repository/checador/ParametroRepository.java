package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, String>
{
    public Parametro findByNombre(String Nombre);


}
