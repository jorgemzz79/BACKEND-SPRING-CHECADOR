package com.backend.APIRest.service.asistencia;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.repository.checador.ChecadaRepository;
import com.backend.APIRest.service.checada.ChecadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {
    // Formato para fechas de entrada
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    // Formato para fechas de salida
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    private ChecadaRepository checadaRepository;

    @Override
    public List<Checada> saveChecadas(List<Checada> checadas) {
        return checadaRepository.saveAll(checadas);
    }

    @Override
    public List<Checada> getAllChecadas() {
        return checadaRepository.findAll();
    }

    @Override
    public Optional<Checada> getChecadaById(Integer id) {
        return checadaRepository.findById(id);
    }

    @Override
    public Checada updateChecada(Integer id, Checada checada) {
        if (checadaRepository.existsById(id)) {
            checada.setId(id);
            return checadaRepository.save(checada);
        }
        return null;
    }

    @Override
    public void deleteChecada(Integer id) {
        checadaRepository.deleteById(id);
    }

    public Page<Checada> getChecadasPaginated(Pageable pageable) {
        return checadaRepository.findAll(pageable);
    }

    @Override
    public Page<Checada> getChecadasByCol1AndDateRange(Integer col1, String startDate, String endDate, Pageable pageable) {
        try {
            LocalDateTime inicio = convertToDate(startDate).atStartOfDay();
            LocalDateTime fin = convertToDate(endDate).atTime(23, 59, 59);

            // Imprimir las fechas para depuración
            System.out.println("==================================================================================================");
            System.out.println("Inicio: " + inicio);
            System.out.println("Fin: " + fin);
            System.out.println("==================================================================================================");

            return checadaRepository.findByCol1AndCol2Between(col1, inicio, fin, pageable);
        } catch (DateTimeParseException e) {
            // Manejar errores de análisis de fechas
            throw new RuntimeException("Invalid date format. Start: " + startDate + ", End: " + endDate, e);
        }
    }

    // Método para convertir la cadena de fecha a LocalDate
    private LocalDate convertToDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format: " + dateString, e);
        }
    }
}
