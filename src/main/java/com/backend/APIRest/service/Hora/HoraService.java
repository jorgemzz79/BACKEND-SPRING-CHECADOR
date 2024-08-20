package com.backend.APIRest.service.Hora;

import com.backend.APIRest.model.dto.hora.HorarioDiaDto;
import com.backend.APIRest.model.entidades.checador.Hora;

import java.util.Date;
import java.util.List;

public interface HoraService {

    List<Hora> saveAll(List<Hora> horas);

    List<HorarioDiaDto> buscarAsistencia(String dia, Date fecha);

    List<HorarioDiaDto> buscarAsistenciaPorRangoFechas(Date fechaInicio, Date fechaFin);
}
