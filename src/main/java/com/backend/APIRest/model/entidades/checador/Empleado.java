package com.backend.APIRest.model.entidades.checador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "empleados")
public class Empleado
{


    public Empleado(Integer idEmpleado)
    {
        this.setIdEmpleado(idEmpleado);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @NotNull
    @Size(max = 100)
    @Column(name = "primer_apellido")
    private String primerApellido;

    @NotNull
    @Size(max = 100)
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @PrePersist
    public void prePersist() {
        if (this.fechaBaja == null) {
            this.fechaBaja = LocalDate.of(1900, 1, 1); // Valor predeterminado de fecha de baja
        }
    }

    @Size(max = 100)
    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Size(max = 100)
    @Column(name = "titulo_universitario")
    private String tituloUniversitario;

    @Size(max = 10)
    @Pattern(regexp="^\\d{10}$", message="El número de contacto debe tener 10 dígitos")
    @Column(name = "contacto")
    private String contacto;

    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
}
