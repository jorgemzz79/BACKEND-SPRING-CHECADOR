package com.backend.APIRest.controlller;

import com.backend.APIRest.model.dto.jcas.PagoDatos;
import com.backend.APIRest.model.dto.jcas.RespuestaPago;
import com.backend.APIRest.model.dto.jcas.SaldoDatos;
import com.backend.APIRest.model.dto.response.respuesta.Respuesta;
import com.backend.APIRest.service.jcas.JcasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JcasController
{
    @Autowired
    private JcasService jcasService;


   // @PreAuthorize("hasRole('GetSaldo')")
    @GetMapping("/Pago/")
    public SaldoDatos GetSaldo(@RequestParam(required = true) String cuenta, @RequestParam(required = true) int usuario, @RequestParam(required = true) String password)
    {
        SaldoDatos saldoDatos=  jcasService.consultarAdeudo(cuenta,usuario,password);
        new Respuesta<>(HttpStatus.CONTINUE,saldoDatos,saldoDatos.getRespuesta().getDescRespuesta(),"GetSaldo>Cuenta>"+cuenta);
        return saldoDatos;
    }

    @PostMapping("/Pago/")
    public RespuestaPago Pago(@RequestParam(required = true) PagoDatos pagoDatos)
    {
        RespuestaPago respuestaPago= null;



        // jcasService.consultarAdeudo(cuenta,usuario,password);
       //  new Respuesta<>(HttpStatus.CONTINUE,respuestaPago,"","");

        return respuestaPago;
    }

    @GetMapping("/reciboPDF/")
    public void reciboPDF(HttpServletResponse httpServletResponse, @RequestParam(required = false) String cuenta)
    {

        try
        {
            jcasService.generarReciboPDF(httpServletResponse,  cuenta);
            new Respuesta<>(HttpStatus.CONTINUE,null,"REPORTE GENERADO","reciboPDF>Cuenta>"+cuenta);

        }
        catch (DataAccessException excepcion)
        {
            System.out.println("---ERROR:"+excepcion.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("------------ERROR:"+e.getMessage());
        }
    }
    @GetMapping("/fechaVencimiento/")
    public LocalDate fechaVencimiento(HttpServletResponse httpServletResponse, @RequestParam(required = false) String cuenta)
    {
        LocalDate fechaVenc = null;
        try
        {
           fechaVenc= jcasService.fechaVencimiento(cuenta);
            if (fechaVenc == null) {
                fechaVenc = LocalDate.of(1900, 1, 1);
            }
            new Respuesta<>(HttpStatus.CONTINUE,null,"ConsultaFechaVencimiento","fechaVencimiento>Cuenta>"+cuenta);
        }
        catch (DataAccessException excepcion)
        {
            System.out.println("---ERROR:"+excepcion.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("------------ERROR:"+e.getMessage());
        }
        return fechaVenc;
    }

}
