package com.backend.APIRest.service.parametros;

import com.backend.APIRest.model.entidades.checador.Parametro;

import java.util.List;

public interface ParametroService {
    public List<Parametro> todosLosParametros() throws Exception;

    public Parametro obtenerUnParametro(String nombre);
}
