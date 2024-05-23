package com.backend.APIRest.service.jcas;

import com.backend.APIRest.model.dto.jcas.ExcepcionJcas;
import com.backend.APIRest.model.dto.jcas.SaldoDatos;


import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface JcasService {


    public SaldoDatos consultarAdeudo(String cuenta, int usuario, String password) throws ExcepcionJcas;


    public void generarReciboPDF(HttpServletResponse httpServletResponse, String cuenta) throws Exception;

    public LocalDate fechaVencimiento(String cuenta);


}
