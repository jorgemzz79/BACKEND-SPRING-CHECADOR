package com.backend.APIRest.service.xlsChecada;

import com.backend.APIRest.model.entidades.checador.XlsChecada;

import java.util.List;

public interface XlsChecadaService {
    List<XlsChecada> saveAll(List<XlsChecada> xlsChecadas);
}
