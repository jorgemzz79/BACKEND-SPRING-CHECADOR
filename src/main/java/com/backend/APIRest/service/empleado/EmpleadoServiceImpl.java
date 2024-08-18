package com.backend.APIRest.service.empleado;

import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.repository.checador.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Override
    public Empleado getEmpleadoById(Integer idEmpleado) {
        return empleadoRepository.findById(idEmpleado).orElse(null);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado createEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado updateEmpleado(Integer id, Empleado empleadoDetails) {
        Empleado empleado = getEmpleadoById(id);
        if (empleado != null) {
            empleado.setNombreEmpleado(empleadoDetails.getNombreEmpleado());
            empleado.setPrimerApellido(empleadoDetails.getPrimerApellido());
            empleado.setSegundoApellido(empleadoDetails.getSegundoApellido());
            empleado.setFechaNacimiento(empleadoDetails.getFechaNacimiento());
            empleado.setFechaAlta(empleadoDetails.getFechaAlta());
            empleado.setFechaBaja(empleadoDetails.getFechaBaja());
            empleado.setTipoSangre(empleadoDetails.getTipoSangre());
            empleado.setTituloUniversitario(empleadoDetails.getTituloUniversitario());
            empleado.setContacto(empleadoDetails.getContacto());
            empleado.setDireccion(empleadoDetails.getDireccion());
            return empleadoRepository.save(empleado);
        }
        return null;
    }

    @Override
    public void deleteEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
    }
}
