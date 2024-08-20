package com.backend.APIRest.service.Hora;

import com.backend.APIRest.model.dto.hora.HorarioDiaDto;
import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.repository.checador.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class HoraServiceImpl implements HoraService {

    @Autowired
    private HoraRepository horaRepository;

    @Override
    public List<Hora> saveAll(List<Hora> horas) {
        return horaRepository.saveAll(horas);
    }

    @Override
    public List<HorarioDiaDto> buscarAsistencia(String dia, Date fecha) {
        return horaRepository.findHorariosPorDia(dia, fecha);
    }

    @Override
    public List<HorarioDiaDto> buscarAsistenciaPorRangoFechas(Date fechaInicio, Date fechaFin) {
        List<HorarioDiaDto> resultados = new ArrayList<>();

        // Obtener los días de la semana entre la fecha de inicio y la fecha de fin
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fechaInicio);

        Calendar calFin = Calendar.getInstance();
        calFin.setTime(fechaFin);

        while (!calInicio.after(calFin)) {
            int diaSemana = calInicio.get(Calendar.DAY_OF_WEEK);
            String diaNombre = obtenerNombreDia(diaSemana);

            // Llama a la consulta por cada día de la semana, pasando la fecha correspondiente
            List<HorarioDiaDto> resultadoDia = horaRepository.findHorariosPorDia(diaNombre, calInicio.getTime());
            resultados.addAll(resultadoDia);

            calInicio.add(Calendar.DATE, 1); // Avanza al siguiente día
        }

        return resultados;
    }

    // Método auxiliar para obtener el nombre del día de la semana en formato de string
    private String obtenerNombreDia(int diaSemana) {
        switch (diaSemana) {
            case Calendar.MONDAY: return "lunes";
            case Calendar.TUESDAY: return "martes";
            case Calendar.WEDNESDAY: return "miércoles";
            case Calendar.THURSDAY: return "jueves";
            case Calendar.FRIDAY: return "viernes";
            case Calendar.SATURDAY: return "sábado";
            case Calendar.SUNDAY: return "domingo";
            default: return "";
        }
    }
}
