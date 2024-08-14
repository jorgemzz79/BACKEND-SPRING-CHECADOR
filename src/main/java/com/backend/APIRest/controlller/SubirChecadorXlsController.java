package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.service.xlsChecada.XlsChecadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SubirChecadorXlsController {

    @Autowired
    private XlsChecadaService xlsChecadaService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/subirChecadorXls")
    public ResponseEntity<Void> subirChecadorXls(@RequestParam("file") MultipartFile file) {
        try {
            List<Checada> checadas = parseTextFile(file);
            xlsChecadaService.saveAll(checadas);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Checada> parseTextFile(MultipartFile file) throws IOException {
        List<Checada> checadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber <= 4) {
                    continue; // skip header lines
                }

                String[] columns = line.split("\t");
                if (columns.length < 5) {
                    continue; // skip invalid lines
                }

                // Skip lines that start with "Revisado por" or "Fecha/Hora"
                if (columns[0].trim().equalsIgnoreCase("Revisado por") ||
                        columns[0].trim().startsWith("Fecha/Hora")) {
                    continue;
                }

                Checada checada = new Checada();
                checada.setEmpleado(new Empleado(Integer.valueOf(columns[0].trim())));

                checada.setFechaHora(LocalDateTime.parse(columns[2].trim()));
                checada.setCodigoTrabajo(columns[3].trim());
                checada.setTipoRegistro(columns[4].trim());

                checadas.add(checada);
            }
        }

        return checadas;
    }
}
