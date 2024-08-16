package com.backend.APIRest.model.dto.hora;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalTime;

public class HorarioDiaDto {
    private Integer idEmpleado;
    private String entradaSalida;
    private LocalTime horario;
    private LocalTime desde;
    private LocalTime hasta;
    private LocalTime puntual;
    private LocalTime fuera;
}
