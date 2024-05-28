package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.service.checada.ChecadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checadas")
public class ChecadaController {

    @Autowired
    private ChecadaService checadaService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<List<Checada>> createChecada(@RequestBody List<Checada> checadas) {
        List<Checada> newChecadas = checadaService.saveChecadas(checadas);
        return ResponseEntity.ok(newChecadas);
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
    // Nuevo endpoint para paginación
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Checada>> getChecadasPaginated(Pageable pageable) {
        Page<Checada> checadas = checadaService.getChecadasPaginated(pageable);
        return ResponseEntity.ok(checadas);
    }
}