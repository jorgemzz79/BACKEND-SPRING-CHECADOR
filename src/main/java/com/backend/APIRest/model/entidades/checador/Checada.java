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

@Table(name = "Checadas")

public class Checada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "perfil_nombre")
    private Empleado empleado;
    private String nombreEmpleado;
    private LocalDateTime fechaHora;
    private String codigoTrabajo;
    private String tipoRegistro;





}
