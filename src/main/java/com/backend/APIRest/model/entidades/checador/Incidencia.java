package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;

import java.time.LocalTime;


    @Entity
    @Table(name = "incidencias")
    public class Incidencia {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idIncidencia")
        private Integer idIncidencia;

        @Column(name = "noEmpleado")
        private Integer noEmpleado;

        @Column(name = "fechaHora")
        private LocalTime fechaHora;

        @Column(name = "tipo")
        private String tipo;

        @Column(name = "descripcion")
        private String descripcion;

        // Getters and setters
        // ...
    }

