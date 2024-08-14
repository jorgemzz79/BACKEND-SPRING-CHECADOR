package com.backend.APIRest.service.xlsChecada;


import com.backend.APIRest.model.entidades.checador.Checada;
import com.backend.APIRest.repository.checador.XlsChecadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class XlsChecadaServiceImpl implements XlsChecadaService {

    @Autowired
    private XlsChecadaRepository xlsChecadaRepository;

    @Override
    public List<Checada> saveAll(List<Checada> checadas) {
        return xlsChecadaRepository.saveAll(checadas);
    }
}