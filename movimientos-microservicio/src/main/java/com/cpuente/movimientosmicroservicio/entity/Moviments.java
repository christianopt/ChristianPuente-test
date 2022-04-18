package com.cpuente.movimientosmicroservicio.entity;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Data
@Table(name ="Movimientos")
@AllArgsConstructor @NoArgsConstructor
public class Moviments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientosid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String tipomovimiento;
    private Double valor;
    private Double saldo;
    private Long cuentaid;
    private String estado;

}
