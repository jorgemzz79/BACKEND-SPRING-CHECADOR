package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIncidencia")
    private Integer idIncidencia;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @NotNull
    @Column(name = "fechaHoraDesde")
    private LocalDateTime fechaHoraDesde;

    @NotNull
    @Column(name = "fechaHoraHasta")
    private LocalDateTime fechaHoraHasta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "empleado_autorizo_id")
    private Empleado empleadoAutorizo;

    @NotNull
    @Size(max = 200)
    @Column(name = "observacion")
    private String observacion;
}

