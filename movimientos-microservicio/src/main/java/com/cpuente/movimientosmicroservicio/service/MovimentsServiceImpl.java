package com.cpuente.movimientosmicroservicio.service;

import com.cpuente.movimientosmicroservicio.entity.Moviments;
import com.cpuente.movimientosmicroservicio.repository.MovimentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentsServiceImpl implements MovimentsService {
    private final MovimentsRepository movimentsRepository;

    @Override
    public List<Moviments> listAllMoviments() {
        return movimentsRepository.findAll();
    }

    @Override
    public Moviments getMoviments(Long id) {
        return movimentsRepository.findById(id).orElse(null);
    }

    @Override
    public Moviments createMoviments(Moviments moviments) {
        moviments.setEstado("True");
        moviments.setFecha(new Date());
        return movimentsRepository.save(moviments);
    }

    @Override
    public Moviments updateMoviments(Moviments moviments) {
        Moviments movimentsDB = getMoviments(moviments.getMovimientosid());
        if (null == movimentsDB) {
            return null;
        }
        movimentsDB.setTipomovimiento(moviments.getTipomovimiento());
        movimentsDB.setValor(moviments.getValor());
        movimentsDB.setSaldo(moviments.getSaldo());
        movimentsDB.setCuentaid(moviments.getCuentaid());
        movimentsDB.setEstado(moviments.getEstado());
        return movimentsRepository.save(movimentsDB);
    }

    @Override
    public Moviments deleteMoviments(Long id) {
        Moviments movimentsDB = getMoviments(id);
        if (null == movimentsDB) {
            return null;
        }
        movimentsDB.setEstado("False");
        return movimentsRepository.save(movimentsDB);
    }

    @Override
    public List<Moviments> getMovimentsByDateRange(Date fechaInicial, Date fechaFinal) {
        return movimentsRepository.findByFechaBetween(fechaInicial, fechaFinal);
    }
}
