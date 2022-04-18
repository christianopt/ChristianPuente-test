package com.cpuente.movimientosmicroservicio.service;

import com.cpuente.movimientosmicroservicio.entity.Moviments;

import java.util.Date;
import java.util.List;

public interface MovimentsService {
    public List<Moviments> listAllMoviments();

    public Moviments getMoviments(Long id);

    public Moviments createMoviments(Moviments moviments);

    public Moviments updateMoviments(Moviments moviments);

    public Moviments deleteMoviments(Long id);

    public List<Moviments> getMovimentsByDateRange(Date fechaIncial, Date fechaFinal);
}