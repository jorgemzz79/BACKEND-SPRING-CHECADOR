package com.backend.APIRest.model.entidades.checador;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Table(name = "checadas")

public class Checada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
    private LocalDateTime fechaHora;
    private String codigoTrabajo;
    private String tipoRegistro;





}
