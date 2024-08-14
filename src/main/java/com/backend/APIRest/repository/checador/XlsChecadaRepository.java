package com.backend.APIRest.repository.checador;

import com.backend.APIRest.model.entidades.checador.Checada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XlsChecadaRepository extends JpaRepository<Checada, Integer> {
}
