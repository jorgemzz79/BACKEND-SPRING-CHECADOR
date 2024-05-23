package com.backend.APIRest.service.datosRecibo;

import com.backend.APIRest.model.dto.datosRecibo.DatosRecibo;
import com.backend.APIRest.model.entidades.comercial.DatosReciboView;
import com.backend.APIRest.model.entidades.checador.Parametro;
import com.backend.APIRest.repository.comercial.DatosReciboRepository;
import com.backend.APIRest.service.parametros.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DatosReciboServiceImpl implements DatosReciboService{

    @Autowired
    private DatosReciboRepository datosReciboRepository;

    @Autowired
    private ParametroService parametroService;

    private List<DatosReciboView> consultarBD (String cuenta) throws Exception
    {
//        if(desde==null && hasta==null && ruta==null) {
//            throw new Exception("Debe especificar al menos un filtro");
//        }
//        else
//        {
//            if (ruta == null)
//                ruta = "0";
//
//            if (desde == null && hasta == null) {
//                desde = "0000000000";
//                hasta = "9999999999";
//            }
//            if (desde == null && hasta != null)
//                desde = hasta;
//
//            if (desde != null && hasta == null)
//                hasta = desde;

            return datosReciboRepository.datosRecibo(cuenta);


    }

    private List<DatosRecibo> convertir (List<DatosReciboView> datosRecibosMySqlView, Parametro montoParaVencer, Parametro diasSinPagoParaVencer)
    {
        List<DatosRecibo> datosRecibosV2 = new ArrayList<>();
        String cuenta="";
        DatosRecibo datosRecibo = null;

        for (DatosReciboView datoReciboMySql : datosRecibosMySqlView)
        {
            if(datoReciboMySql.getCuenta().equals(cuenta))
            {
                if(datoReciboMySql.getConcepto()!=null)
                    datosRecibo.AgregarConcepto(datoReciboMySql);
            }
            else
            {
                if(cuenta !="" )
                {
                    datosRecibosV2.add(datosRecibo);
                    datosRecibo =null;
                }
                datosRecibo = new DatosRecibo(datoReciboMySql,montoParaVencer,diasSinPagoParaVencer);
                if(datoReciboMySql.getConcepto()!=null)
                    datosRecibo.AgregarConcepto(datoReciboMySql);
                cuenta=datoReciboMySql.getCuenta();
            }
        }
        datosRecibosV2.add(datosRecibo);

        return datosRecibosV2;
    }
    @Override
    public List<DatosRecibo> obtenerDatosRecibo(String cuenta) throws Exception
    {
        Parametro venceAlMontoRezago=parametroService.obtenerUnParametro("VENCE_AL_MONTO_REZAGO");
        Parametro venceALosDiasSinPago=parametroService.obtenerUnParametro("VENCE_A_LOS_DIAS_SIN_PAGO");

        List<DatosReciboView> datosReciboView=consultarBD(cuenta);
        if (datosReciboView.isEmpty())
            return null;
        else
       return convertir(datosReciboView,venceAlMontoRezago,venceALosDiasSinPago);
    }

    @Override
    public LocalDate obtenerFechaVencimiento(String cuenta)  {
        return datosReciboRepository.fechaVencimiento(cuenta);
    }
}
