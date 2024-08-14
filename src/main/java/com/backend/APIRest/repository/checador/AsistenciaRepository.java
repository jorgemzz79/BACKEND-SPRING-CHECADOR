package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Checada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AsistenciaRepository extends JpaRepository<Checada, Integer> {
    @Query("SELECT c FROM Checada c WHERE c.NoEmpleado = :NoEmpleado AND c.FechaHora BETWEEN :startDate AND :endDate")
    Page<Checada> findByCol1AndCol2Between(@Param("NoEmpleado") Integer col1, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}

