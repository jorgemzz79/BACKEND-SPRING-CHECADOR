package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "empleados")
public class Empleado {


    public Empleado(Integer idEmpleado)
    {
        this.setIdEmpleado(idEmpleado);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Column(name = "titulo_universitario")
    private String tituloUniversitario;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "direccion")
    private String direccion;


}
=======

public class Empleado {
    @Entity
    @Table(name = "empleados")
    public class Empleado {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_empleado")
        private Integer idEmpleado;

        @Column(name = "nombre_empleado")
        private String nombreEmpleado;

        @Column(name = "primer_apellido")
        private String primerApellido;

        @Column(name = "segundo_apellido")
        private String segundoApellido;

        @Column(name = "fecha_nacimiento")
        private String fechaNacimiento;

        @Column(name = "tipo_sangre")
        private String tipoSangre;

        @Column(name = "titulo_universitario")
        private String tituloUniversitario;

        @Column(name = "contacto")
        private String contacto;

        @Column(name = "direccion")
        private String direccion;

        // Getters and Setters
    }

}
>>>>>>> 9830d0898d405ddf4eeabbcb7a88e329c60c01df
