package com.backend.APIRest.model.entidades.checador;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "checadas",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"empleado_id", "fechaHora"})})
@SQLInsert(sql = "INSERT INTO checadas (empleado_id, fechaHora) VALUES (?, ?) " +
        "ON DUPLICATE KEY UPDATE empleado_id = empleado_id")

public class Checada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @NotNull
    private LocalDateTime fechaHora;

}
