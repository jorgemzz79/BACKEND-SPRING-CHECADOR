package com.backend.APIRest.service.checada;

import com.backend.APIRest.model.entidades.checador.Checada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChecadaService {
    List<Checada> saveChecadas(List<Checada> checadas);
    List<Checada> getAllChecadas();
    Optional<Checada> getChecadaById(Integer id);
    Checada updateChecada(Integer id, Checada checada);
    void deleteChecada(Integer id);

    Page<Checada> getChecadasPaginated(Pageable pageable);
    // Método para paginación
   
}
