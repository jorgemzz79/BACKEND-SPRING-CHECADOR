package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.service.empleado.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    @CrossOrigin(origins = "http://localhost:4200")

    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public Empleado createEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.createEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoDetails) {
        Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleadoDetails);
        return ResponseEntity.ok(updatedEmpleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
