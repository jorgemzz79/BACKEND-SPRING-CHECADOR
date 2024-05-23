package com.backend.APIRest.service.checada;

import com.backend.APIRest.model.entidades.checador.Checada;

import java.util.List;
import java.util.Optional;

public interface ChecadaService {
    Checada saveChecada(Checada checada);
    List<Checada> getAllChecadas();
    Optional<Checada> getChecadaById(Integer id);
    Checada updateChecada(Integer id, Checada checada);
    void deleteChecada(Integer id);
}
