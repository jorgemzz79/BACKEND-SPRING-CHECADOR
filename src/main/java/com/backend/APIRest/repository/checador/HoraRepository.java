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

import java.util.List;

@Repository
public interface HoraRepository extends JpaRepository<Hora, Integer> {
    @Query(value = "select *,\n" +
            "(SELECT TIME(fechaHora) AS checada FROM checadas WHERE DATE(fechaHora)='2024-08-01' and time(fechaHora) BETWEEN \n" +
            "\n" +
            "desde and hasta and checadas.empleado_id=x.id_empleado limit 1) as puntual\n" +
            ",\n" +
            "(SELECT TIME(fechaHora) AS checada FROM checadas WHERE DATE(fechaHora)='2024-08-01' and time(fechaHora) BETWEEN \n" +
            "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(desde)-3600), '%H:%i:%s')  and TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(hasta)+3600), '%H:%i:%s') and checadas.empleado_id=x.id_empleado limit 1) as fuera   from (\n" +
            "SELECT e.id_empleado, \n" +
            "h.entrada_salida,TIME_FORMAT( (\n" +
            "CASE :dia\n" +
            "WHEN 'lunes' THEN h.lunes \n" +
            "WHEN 'martes' THEN h.martes \n" +
            "WHEN 'miercoles' THEN h.miercoles \n" +
            "WHEN 'jueves' THEN h.jueves \n" +
            "WHEN 'viernes' THEN h.viernes \n" +
            "WHEN 'sabado' THEN h.sabado \n" +
            "WHEN 'domingo' THEN h.domingo END), '%H:%i:%s') as horario,\n" +
            "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(\n" +
            "CASE :dia\n" +
            "WHEN 'lunes' THEN h.lunes \n" +
            "WHEN 'martes' THEN h.martes \n" +
            "WHEN 'miercoles' THEN h.miercoles \n" +
            "WHEN 'jueves' THEN h.jueves \n" +
            "WHEN 'viernes' THEN h.viernes \n" +
            "WHEN 'sabado' THEN h.sabado \n" +
            "WHEN 'domingo' THEN h.domingo END) - \n" +
            "(IF(h.entrada_salida = 'entrada', mt.entrada_minutos_antes, mt.salida_minutos_antes) * 60)), '%H:%i:%s') AS desde, \n" +
            "TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC( CASE :dia\n" +
            "WHEN 'lunes' THEN h.lunes \n" +
            "WHEN 'martes' THEN h.martes \n" +
            "WHEN 'miercoles' THEN h.miercoles \n" +
            "WHEN 'jueves' THEN h.jueves \n" +
            "WHEN 'viernes' THEN h.viernes \n" +
            "WHEN 'sabado' THEN h.sabado \n" +
            "WHEN 'domingo' THEN h.domingo END) + \n" +
            "(IF(h.entrada_salida = 'entrada', mt.entrada_minutos_despues, mt.salida_minutos_despues) * 60)), '%H:%i:%s') AS hasta \n" +
            "FROM empleados e \n" +
            "INNER JOIN horas h ON e.id_empleado = h.empleado_id \n" +
            "INNER JOIN margentiempo mt ON 1 = 1 )x", nativeQuery = true)
    List<HorarioDiaDto> findHorariosPorDia(@Param("dia") String dia);

}