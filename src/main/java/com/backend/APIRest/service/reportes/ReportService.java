package com.backend.APIRest.service.reportes;

import com.backend.APIRest.model.dto.response.respuesta.Respuesta;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class ReportService {


    public void generarReporte(HttpServletResponse httpServletResponse, String reporte, Collection<?> datos) throws IOException, JRException {
        // Establecer el tipo de contenido como PDF
        httpServletResponse.setContentType("application/pdf");

        // Establecer el encabezado Content-Disposition para indicar el nombre del archivo y si debe abrirse en el navegador o descargarse
        String filename = reporte + "_" + LocalDateTime.now() + ".pdf";
        String inlineOrAttachment = "inline"; // Puedes cambiar esto a "attachment" si deseas que se descargue en lugar de mostrarse en línea
        httpServletResponse.setHeader("Content-Disposition", String.format("%s; filename=\"%s\"", inlineOrAttachment, filename));

        // Establecer encabezados adicionales para controlar el caché del navegador
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpServletResponse.setDateHeader("Expires", 0); // Proxies.

        // Compilar el informe Jasper
        File file = ResourceUtils.getFile("\\\\192.168.1.100\\sistema_integral\\REPORTES\\RECIBOS\\" + reporte + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("createdBy", "Sistemas");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Exportar el informe a un flujo de salida PDF
        JasperExportManager.exportReportToPdfStream(jasperPrint, httpServletResponse.getOutputStream());

    }

}
