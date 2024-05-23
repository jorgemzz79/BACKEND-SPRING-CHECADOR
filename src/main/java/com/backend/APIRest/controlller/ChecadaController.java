package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.service.checada.ChecadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checadas")
public class ChecadaController {

    @Autowired
    private ChecadaService checadaService;

    @PostMapping
    public ResponseEntity<Checada> createChecada(@RequestBody Checada checada) {
        Checada newChecada = checadaService.saveChecada(checada);
        return ResponseEntity.ok(newChecada);
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
}