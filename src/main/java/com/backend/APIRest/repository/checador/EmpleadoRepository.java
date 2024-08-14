package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Checada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface EmpleadoRepository {
    @Query("SELECT c FROM Empleado c WHERE c.noEmpleado = :NoEmpleado AND c.fechaHora BETWEEN :startDate AND :endDate")
    Page<Checada> findByCol1AndCol2Between(@Param("NoEmpleado") Integer col1, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}

