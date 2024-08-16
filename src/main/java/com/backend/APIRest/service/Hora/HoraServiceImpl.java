package com.backend.APIRest.service.Hora;


import com.backend.APIRest.model.dto.hora.HorarioDiaDto;
import com.backend.APIRest.model.entidades.checador.Hora;

import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.repository.checador.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<HorarioDiaDto> buscarAsistencia(String dia) {

        //meter ciclo


        return horaRepository.findHorariosPorDia(dia);
    }
}