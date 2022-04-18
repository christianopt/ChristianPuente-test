package com.cpuente.cuentamicroservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name="Cuenta")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaid;
    @NotEmpty(message = "La cuenta no debe ser vacía")
    @NotNull(message = "La cuenta no debe ser vacía")
    @Size( min = 6 ,  message = "El tamaño del número de cuenta debe ser mayor a seis")
    @Column(name = "numerocuenta" , nullable = false)
    private String numerocuenta;
    @NotEmpty(message = "El tipo de cuenta no debe ser vacío")
    private String tipocuenta;
    @Positive(message="El saldo inicial debe ser mayor a cero")
    private Double saldoinicial;
    private String estado ;
    @NotNull(message = "El clienteId no debe ser nulo o vacío")
    private Long clienteid;
}
