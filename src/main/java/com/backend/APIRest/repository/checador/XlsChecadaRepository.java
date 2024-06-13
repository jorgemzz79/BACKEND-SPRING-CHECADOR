package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.model.entidades.checador.XlsChecada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XlsChecadaRepository extends JpaRepository<XlsChecada, Integer> {
}
