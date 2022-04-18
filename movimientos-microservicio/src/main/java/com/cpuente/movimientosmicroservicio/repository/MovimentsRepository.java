package com.cpuente.movimientosmicroservicio.repository;

import com.cpuente.movimientosmicroservicio.entity.Moviments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimentsRepository extends JpaRepository<Moviments, Long> {
    public List<Moviments> findByFechaBetween(Date fechaIncial, Date fechaFinal);
}
