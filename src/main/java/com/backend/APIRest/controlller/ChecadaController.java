package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.service.checada.ChecadaService;
import com.backend.APIRest.service.empleado.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checadas")
public class ChecadaController {

    @Autowired
    private ChecadaService checadaService;

    @Autowired
    private EmpleadoService empleadoService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<List<Checada>> createChecada(@RequestParam("file") MultipartFile file) {
        System.out.println("INICIANDO ------------------------------->");
        try {
            List<Checada> checadas = parseTextFile(file); System.out.println("PARSE ------------------------------->");
            List<Checada> newChecadas = checadaService.saveChecadas(checadas);System.out.println("LISTA ------------------------------->");
            return ResponseEntity.ok(newChecadas);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Checada> parseTextFile(MultipartFile file) throws IOException {
        List<Checada> checadas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
                Empleado empleado= empleadoService.obtenerEmpleadoPorId(Integer.valueOf(columns[0].trim()));
                if(empleado!=null)
                {
                checada.setEmpleado( empleado );
                checada.setFechaHora(LocalDateTime.parse(columns[2].trim(), formatter));
                checada.setCodigoTrabajo(columns[3].trim());
                checada.setTipoRegistro(columns[4].trim());
                    System.out.println("checada ------------------------------->" + checada.getEmpleado().getNombreEmpleado());

                checadas.add(checada);
                }
                else{
                    System.out.println("No se encontro el empleado--->");

                }
            }
        }

        return checadas;
    }

    @GetMapping
    public ResponseEntity<List<Checada>> getAllChecadas() {
        List<Checada> checadas = checadaService.getAllChecadas();
        return ResponseEntity.ok(checadas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checada> getChecadaById(@PathVariable Integer id) {
        Optional<Checada> checada = checadaService.getChecadaById(id);
        return checada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Checada> updateChecada(@PathVariable Integer id, @RequestBody Checada checada) {
        Checada updatedChecada = checadaService.updateChecada(id, checada);
        return updatedChecada != null ? ResponseEntity.ok(updatedChecada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecada(@PathVariable Integer id) {
        checadaService.deleteChecada(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Checada>> getChecadasPaginated(Pageable pageable) {
        Page<Checada> checadas = checadaService.getChecadasPaginated(pageable);
        return ResponseEntity.ok(checadas);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/checadas/filter")
    public ResponseEntity<Page<Checada>> getChecadasByCol1AndDateRange(
            @RequestParam("NoEmpleado") Integer NoEmpleado,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Pageable pageable) {
        Page<Checada> checadas = checadaService.getChecadasByCol1AndDateRange(NoEmpleado, startDate, endDate, pageable);
        return ResponseEntity.ok(checadas);
    }
}
