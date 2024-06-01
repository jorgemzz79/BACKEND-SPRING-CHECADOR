package com.backend.APIRest.service.Hora;


import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.repository.checador.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoraService {

    @Autowired
    private HoraRepository horaRepository;

    public void saveAll(List<Hora> horas) {
        horaRepository.saveAll(horas);
    }
}