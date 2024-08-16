package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="perfiles")
public class Perfil {
    @Id
    @Size(min = 4, max = 50, message = " debe tener entre 4 y 50 caracteres")
    @NotNull
    @Column(unique = true)
    private String nombre;

    @OneToMany(mappedBy = "perfil",fetch = FetchType.EAGER)
    private List<Usuario> usuarios= new ArrayList<>();;

    @OneToMany(mappedBy = "perfil",fetch = FetchType.EAGER)
    private List<Privilegio> privilegios= new ArrayList<>();;


    public Perfil(@NonNull String nombre) {
        this.nombre = nombre;
    }
}