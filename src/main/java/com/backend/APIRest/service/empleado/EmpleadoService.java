package com.backend.APIRest.service.empleado;

import com.backend.APIRest.model.entidades.checador.Empleado;

import java.util.List;

public interface EmpleadoService {
    public Empleado getEmpleadoById(Integer idEmpleado);
    List<Empleado> getAllEmpleados();
    Empleado createEmpleado(Empleado empleado);
    Empleado updateEmpleado(Integer id, Empleado empleadoDetails);
    void deleteEmpleado(Integer id);
}
