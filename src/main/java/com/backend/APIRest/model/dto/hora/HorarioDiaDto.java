package com.backend.APIRest.model.dto.hora;

import java.time.LocalTime;

public class HorarioDiaDto {
    private Integer idEmpleado;
    private String entradaSalida;
    private LocalTime horario;
    private LocalTime desde;
    private LocalTime hasta;
    private LocalTime puntual;
    private LocalTime fuera;

    // Constructor por defecto
    public HorarioDiaDto() {
    }

    // Getters y Setters
    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEntradaSalida() {
        return entradaSalida;
    }

    public void setEntradaSalida(String entradaSalida) {
        this.entradaSalida = entradaSalida;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public LocalTime getDesde() {
        return desde;
    }

    public void setDesde(LocalTime desde) {
        this.desde = desde;
    }

    public LocalTime getHasta() {
        return hasta;
    }

    public void setHasta(LocalTime hasta) {
        this.hasta = hasta;
    }

    public LocalTime getPuntual() {
        return puntual;
    }

    public void setPuntual(LocalTime puntual) {
        this.puntual = puntual;
    }

    public LocalTime getFuera() {
        return fuera;
    }

    public void setFuera(LocalTime fuera) {
        this.fuera = fuera;
    }
}
