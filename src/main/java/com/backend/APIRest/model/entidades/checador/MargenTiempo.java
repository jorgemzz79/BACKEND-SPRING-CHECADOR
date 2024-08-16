package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "margentiempo")
public class MargenTiempo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_margen_tiempo")
    private Integer idMargenTiempo;

    @Column(name = "entrada_minutos_antes", nullable = false)
    @PositiveOrZero
    private Integer entradaMinutosAntes;

    @Column(name = "entrada_minutos_despues", nullable = false)
    @PositiveOrZero
    private Integer entradaMinutosDespues;

    @Column(name = "salida_minutos_antes", nullable = false)
    @PositiveOrZero
    private Integer salidaMinutosAntes;

    @Column(name = "salida_minutos_despues", nullable = false)
    @PositiveOrZero
    private Integer salidaMinutosDespues;
}