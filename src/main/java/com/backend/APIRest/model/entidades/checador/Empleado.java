package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;

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
