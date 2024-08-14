package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.model.entidades.checador.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

}

