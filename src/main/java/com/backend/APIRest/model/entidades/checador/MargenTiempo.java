package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "margentiempo")
public class MargenTiempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMargenTiempo")
    private Integer idMargenTiempo;

    @Column(name = "entradaMinutosAntes")
    private LocalTime entradaMinutosAntes;

    @Column(name = "entradaMinutosDespues")
    private LocalTime entradaMinutosDespues;

    @Column(name = "salidaMinutosAntes")
    private LocalTime salidaMinutosAntes;

    @Column(name = "salidaMinutosDespues")
    private LocalTime salidaMinutosDespues;

    // Getters and setters
    // ...
}