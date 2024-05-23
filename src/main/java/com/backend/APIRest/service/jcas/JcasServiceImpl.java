package com.backend.APIRest.service.jcas;

import com.backend.APIRest.model.dto.datosRecibo.ConceptoAdeudo;
import com.backend.APIRest.model.dto.datosRecibo.DatosRecibo;
import com.backend.APIRest.model.dto.jcas.Concepto;
import com.backend.APIRest.model.dto.jcas.ExcepcionJcas;
import com.backend.APIRest.model.dto.jcas.SaldoDatos;
import com.backend.APIRest.model.dto.jcas.TiposRespuesta;
import com.backend.APIRest.service.datosRecibo.DatosReciboService;
import com.backend.APIRest.service.reportes.ReportService;
import com.backend.APIRest.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class JcasServiceImpl implements JcasService{
    @Autowired
    private DatosReciboService datosReciboService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ReportService reportService;


    @Override
    public void generarReciboPDF(HttpServletResponse httpServletResponse, String cuenta) throws Exception {


        List<DatosRecibo> datosRecibo=datosReciboService.obtenerDatosRecibo(cuenta);

        reportService.generarReporte(httpServletResponse,"Recibo2024",datosRecibo);


    }

    @Override
    public LocalDate fechaVencimiento(String cuenta)
    {
        return datosReciboService.obtenerFechaVencimiento(cuenta);
    }
        @Override
    public SaldoDatos consultarAdeudo(String cuenta, int idusuario, String password) throws ExcepcionJcas {
        SaldoDatos saldoDatos= new SaldoDatos();

        try {
            //  ERROR_DE_AUTENTICACION(2, "Error De Autenticación"),
           if(!usuarioService.autenticarUsuario(idusuario,password))
           {
               saldoDatos.setCuenta(cuenta);
               saldoDatos.setNombreUsuario("ERROR");
               saldoDatos.setRespuesta(TiposRespuesta.ERROR_DE_AUTENTICACION);
               return saldoDatos;

           }
            List<DatosRecibo> listaDatosRecibo = datosReciboService.obtenerDatosRecibo(cuenta);

            // CUENTA_NO_ENCONTRADA(3, "Cuenta No Encontrada"),
            if (listaDatosRecibo == null) {
               saldoDatos.setCuenta(cuenta);
               saldoDatos.setNombreUsuario("ERROR");
               saldoDatos.setRespuesta(TiposRespuesta.CUENTA_NO_ENCONTRADA);
               return saldoDatos;
            }

            DatosRecibo datosRecibo = listaDatosRecibo.get(0);

            saldoDatos.setTieneInsen(datosRecibo.getTipoDescuento()!=null);
            saldoDatos.setTieneSubsidio(false);
            saldoDatos.setNoConvenio(datosRecibo.getIdConvenio() != null ? datosRecibo.getIdConvenio() : 0);
            saldoDatos.setCuenta(datosRecibo.getCuenta());
            saldoDatos.setNombreUsuario(datosRecibo.getNombre());
            saldoDatos.setDireccionUsuario(datosRecibo.getDireccion());

            saldoDatos.setSubTotal(saldoDatos.isTieneInsen() ? datosRecibo.getSubtotalAPagarConDescuento() : datosRecibo.getSubtotalAPagarAntesDeDescuento());
            saldoDatos.setIva(saldoDatos.isTieneInsen() ? datosRecibo.getIvaConDescuento() : datosRecibo.getIvaAntesDeDescuento());
            saldoDatos.setSaldo(saldoDatos.getSubtotal() + saldoDatos.getIva());


            List<Concepto> conceptosJcas = new ArrayList<>();

            for (ConceptoAdeudo conceptoAdeudo : datosRecibo.getConceptos()) {
                Concepto concepto = new Concepto();
                concepto.setIdConcepto(conceptoAdeudo.getConcepto());
                concepto.setDescConcepto(conceptoAdeudo.getConceptoNombre());
                concepto.setAdeudo(saldoDatos.isTieneInsen() ? conceptoAdeudo.getAdeudo() - conceptoAdeudo.getDescuento() : conceptoAdeudo.getAdeudo());
                concepto.setTasaIVA((int) (conceptoAdeudo.getTasaIva() * 100));

                concepto.setIvaConcepto(Math.round(concepto.getAdeudo() * (conceptoAdeudo.getTasaIva() / 100)));

                concepto.setCobraIVA(conceptoAdeudo.getTasaIva() > 0);
                conceptosJcas.add(concepto);
            }
            saldoDatos.setConceptos(conceptosJcas);



            //SALDO_EN_CERO(4, "Saldo En Cero, No Se Puede Realizar El Pago"),
            if (saldoDatos.getSaldo() < 1) {
                saldoDatos.setRespuesta(TiposRespuesta.SALDO_EN_CERO);
                return saldoDatos;
            }

            //CUENTA_BLOQUEADA(7, "Cuenta Bloqueada Favor De Pagar en Oficina")
            if (datosRecibo.getObservacion().equals("BLOQUEADA"))
            {
                saldoDatos.setRespuesta(TiposRespuesta.CUENTA_BLOQUEADA);
                return saldoDatos;
            }


                // OK(0, "Ok"),
                saldoDatos.setRespuesta(TiposRespuesta.OK);
            return saldoDatos;

            //MONTO_NO_VALIDO(6, "Monto No Válido"),

            // NO_HAY_TICKET_REGISTRADO(5, "No Hay Un Ticket Registrado Con Esta Cuenta el Día De Hoy"),


        }
        catch (Exception ex) {
            //EXCEPCION_DEL_SISTEMA(1, "Excepción Del Sistema"),
            saldoDatos.setRespuesta(TiposRespuesta.EXCEPCION_DEL_SISTEMA);
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
