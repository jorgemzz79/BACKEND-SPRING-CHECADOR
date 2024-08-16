package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "horas")
public class Hora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "entrada|salida", message = "Debe ser 'entrada' o 'salida'")
    private String entradaSalida;
    private LocalTime lunes;
    private LocalTime martes;
    private LocalTime miercoles;
    private LocalTime jueves;
    private LocalTime viernes;
    private LocalTime sabado;
    private LocalTime domingo;

    // Getters and setters

}
