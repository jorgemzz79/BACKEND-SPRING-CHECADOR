package com.backend.APIRest.service.Hora;

import com.backend.APIRest.model.entidades.checador.Hora;

import java.util.List;

public interface HoraService {
    List<Hora> saveAll(List<Hora> horas);
}
