package com.backend.APIRest.model.entidades.checador;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asistencias")
public class Asistencia
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @ManyToOne
        @JoinColumn(name = "empleado_id")
        private String NombreEmpleado;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime FechaHora;
        private String CodigoTrabajo;
        private String TipoRegistro;

        /*/
        ESTE ES PARA TUNEAR EL DATO ANTES DE MANDARLOS A LA BASE DE DATOS, ES DECIR SI EL DATO ESTA EN MINUSCULAS Y LO QUIERO PONER EN MAYUSCULAS

        public String getCol1() {
            return col1;
        }

        public void setCol1(String col1) {
            this.col1 = col1;
        }
/*/



    }