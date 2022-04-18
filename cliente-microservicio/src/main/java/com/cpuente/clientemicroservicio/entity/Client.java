package com.cpuente.clientemicroservicio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Positive;


@Entity
@Table(name = "Cliente")
@PrimaryKeyJoinColumn(name = "clienteid", referencedColumnName = "personaid")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person implements Serializable {

    @NotEmpty(message = "La contraseña no debe ser vacía")
    private String contrasena;
    private String estado;

    @Builder
    public Client(Long personaId,
                  String nombre, String genero, Integer edad, String identificacion, String direccion, String telefono, String contrasena, String estado) {
        super(personaId, nombre, genero, edad, identificacion, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }


}
