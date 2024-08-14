package com.backend.APIRest.service.xlsChecada;

import com.backend.APIRest.model.entidades.checador.Checada;

import java.util.List;

public interface XlsChecadaService {
    List<Checada> saveAll(List<Checada> checadas);
}
