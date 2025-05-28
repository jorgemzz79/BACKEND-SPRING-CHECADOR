package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.dto.hora.HorarioDiaDto;
import com.backend.APIRest.model.entidades.checador.Hora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.APIRest.model.entidades.checador.Hora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HoraRepository extends JpaRepository<Hora, Integer> {

    @Query(value = "SELECT e.id_empleado, h.entrada_salida, " +
            "TIME_FORMAT(CASE :dia " +
            "WHEN 'lunes' THEN h.lunes " +
            "WHEN 'martes' THEN h.martes " +
            "WHEN 'miercoles' THEN h.miercoles " +
            "WHEN 'jueves' THEN h.jueves " +
            "WHEN 'viernes' THEN h.viernes " +
            "WHEN 'sabado' THEN h.sabado " +
            "WHEN 'domingo' THEN h.domingo END, '%H:%i:%s') AS horario, " +
            "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(CASE :dia " +
            "WHEN 'lunes' THEN h.lunes " +
            "WHEN 'martes' THEN h.martes " +
            "WHEN 'miercoles' THEN h.miercoles " +
            "WHEN 'jueves' THEN h.jueves " +
            "WHEN 'viernes' THEN h.viernes " +
            "WHEN 'sabado' THEN h.sabado " +
            "WHEN 'domingo' THEN h.domingo END) - (IF(h.entrada_salida = 'entrada', mt.entrada_minutos_antes, mt.salida_minutos_antes) * 60)), '%H:%i:%s') AS desde, " +
            "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(CASE :dia " +
            "WHEN 'lunes' THEN h.lunes " +
            "WHEN 'martes' THEN h.martes " +
            "WHEN 'miercoles' THEN h.miercoles " +
            "WHEN 'jueves' THEN h.jueves " +
            "WHEN 'viernes' THEN h.viernes " +
            "WHEN 'sabado' THEN h.sabado " +
            "WHEN 'domingo' THEN h.domingo END) + (IF(h.entrada_salida = 'entrada', mt.entrada_minutos_despues, mt.salida_minutos_despues) * 60)), '%H:%i:%s') AS hasta, " +
            "(SELECT TIME(fechaHora) FROM checadas WHERE DATE(fechaHora)=:fecha AND TIME(fechaHora) BETWEEN desde AND hasta AND checadas.empleado_id=e.id_empleado LIMIT 1) AS puntual, " +
            "(SELECT TIME(fechaHora) FROM checadas WHERE DATE(fechaHora)=:fecha AND TIME(fechaHora) BETWEEN TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(desde)-3600), '%H:%i:%s') AND TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(hasta)+3600), '%H:%i:%s') AND checadas.empleado_id=e.id_empleado LIMIT 1) AS fuera " +
            "FROM empleados e " +
            "INNER JOIN horas h ON e.id_empleado = h.empleado_id " +
            "INNER JOIN margentiempo mt ON 1 = 1",
            nativeQuery = true)
    List<Object[]> findHorariosPorDia(@Param("dia") String dia, @Param("fecha") Date fecha);
}
