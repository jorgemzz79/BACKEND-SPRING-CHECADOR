package com.backend.APIRest.service.empleado;

import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.repository.checador.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Override
    public Empleado obtenerEmpleadoPorId(Integer idEmpleado) {
        return empleadoRepository.findById(idEmpleado).orElse(null);
    }
}
