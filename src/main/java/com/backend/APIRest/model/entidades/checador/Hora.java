package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
@Entity
@Table(name = "horas")
public class Hora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noEmpleado;
    private String entradaSalida;
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;

    public void setNoEmpleado(String stringCellValue) {
    }

    public void setEntradaSalida(String stringCellValue) {
    }

    public void setLunes(String stringCellValue) {
    }

    public void setMartes(String stringCellValue) {
    }

    public void setMiercoles(String stringCellValue) {
    }

    public void setJueves(String stringCellValue) {
    }

    public void setViernes(String stringCellValue) {
    }

    public void setSabado(String stringCellValue) {
    }

    public void setDomingo(String stringCellValue) {
    }

    // Getters and setters
}
