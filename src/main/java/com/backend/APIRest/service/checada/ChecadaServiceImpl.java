package com.backend.APIRest.service.checada;

import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.repository.checador.ChecadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChecadaServiceImpl implements ChecadaService {
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

}
