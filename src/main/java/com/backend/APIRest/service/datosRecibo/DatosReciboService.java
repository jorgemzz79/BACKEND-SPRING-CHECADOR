package com.backend.APIRest.service.datosRecibo;


import com.backend.APIRest.model.dto.datosRecibo.DatosRecibo;

import java.time.LocalDate;
import java.util.List;

public interface DatosReciboService {


    public List<DatosRecibo> obtenerDatosRecibo(String cuenta) throws Exception;
    public LocalDate obtenerFechaVencimiento(String cuenta);

}
