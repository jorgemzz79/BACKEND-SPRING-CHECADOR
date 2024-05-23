package com.backend.APIRest.service.parametros;

import com.backend.APIRest.model.entidades.checador.Parametro;
import com.backend.APIRest.repository.checador.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParametroServiceImpl implements ParametroService{
    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public List<Parametro> todosLosParametros() throws Exception {
        return parametroRepository.findAll();
    }

    @Override
    public Parametro obtenerUnParametro(String nombre) {
        return parametroRepository.findByNombre(nombre);
    }
}
