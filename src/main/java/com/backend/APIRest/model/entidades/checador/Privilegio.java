package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="privilegios")

public class Privilegio
{
        @Id
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 20 caracteres")
        @Column(unique = true)
        private String nombre;

        @ManyToOne
        @JoinColumn(name = "perfil_nombre")
        private Perfil perfil;
        public Privilegio(@NonNull String nombre) {
                this.nombre = nombre;
        }
}

