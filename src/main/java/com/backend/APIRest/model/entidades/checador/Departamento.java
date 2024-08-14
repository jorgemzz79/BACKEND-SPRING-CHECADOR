package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;

    @Entity
    @Table(name = "departamentos")
    public class Departamento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_departamento")
        private Integer idDepartamento;

        @Column(name = "nombre_departamento")
        private String nombreDepartamento;

        @Column(name = "descripcion_departamento")
        private String descripcionDepartamento;

        // Getters and Setters
    }

