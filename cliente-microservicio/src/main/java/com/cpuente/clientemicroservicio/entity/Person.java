package com.cpuente.clientemicroservicio.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Persona")
@Inheritance (strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor @NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaid;
    @NotEmpty(message = "El nombre no debe ser vacío")
    private String nombre;
    private String genero;
    @Positive(message="La edad debe ser mayor a cero")
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;

}
