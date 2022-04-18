package com.cpuente.movimientosmicroservicio.controller;

import com.cpuente.movimientosmicroservicio.entity.Moviments;
import com.cpuente.movimientosmicroservicio.service.MovimentsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimientos")
public class MovimentsController {
    @Autowired
    private MovimentsService movimentsService;

    @GetMapping
    public ResponseEntity<List<Moviments>> listMoviments(@RequestParam(name = "fechaInicio", required = false) String fechaInicio, @RequestParam(name = "fechaFin", required = false) String fechaFin, @RequestParam(name = "cuentaId", required = false) Long cuentaId) {
        List<Moviments> moviments = new ArrayList<>();
        if (null == fechaInicio && null == fechaFin && null == cuentaId) {
            moviments = movimentsService.listAllMoviments();
            if (moviments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            String errores = "";
            SimpleDateFormat formatoValido = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fechaInicioDate = null;
            try {
                fechaInicioDate = formatoValido.parse(fechaInicio);
            } catch (Exception e) {
                errores += "Error en formato fechaInicial. Formato Correcto: dd/MM/yyyy HH:mm:ss \n";
            }
            Date fechaFinDate = null;
            try {
                fechaFinDate = formatoValido.parse(fechaFin);
            } catch (Exception e) {
                errores += "Error en formato fechaFin. Formato Correcto: dd/MM/yyyy HH:mm:ss \n";
            }
            if (!errores.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errores);
            }
            List<Moviments> movimentsRango = movimentsService.getMovimentsByDateRange(fechaInicioDate, fechaFinDate);
            for (Moviments mov : movimentsRango) {
                if (mov.getCuentaid() == cuentaId) {
                    moviments.add(mov);
                }
            }
            if (moviments.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

        }

        return ResponseEntity.ok(moviments);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Moviments> getMoviments(@PathVariable("id") Long id) {
        Moviments moviments = movimentsService.getMoviments(id);
        if (null == moviments) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moviments);
    }

    @PostMapping
    public ResponseEntity<Moviments> createMoviments(@Valid @RequestBody Moviments moviments, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        if (moviments.getTipomovimiento().equalsIgnoreCase("DÉBITO")) {
            if (moviments.getSaldo() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date fechaInicial = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date fechaFinal = cal.getTime();
            List<Moviments> trasaccionesHoy = movimentsService.getMovimentsByDateRange(fechaInicial, fechaFinal);
            Double sumaTransaccionesDebito = 0.0;
            for (Moviments mov : trasaccionesHoy) {
                if (mov.getTipomovimiento().equalsIgnoreCase("DÉBITO") && mov.getCuentaid() == moviments.getCuentaid()) {
                    sumaTransaccionesDebito += mov.getValor();
                }
            }
            if ((sumaTransaccionesDebito + moviments.getValor()) < -1000) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupo diario Excedido");
            }
        }
        Moviments movimentsCreate = movimentsService.createMoviments(moviments);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentsCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Moviments> updateMoviments(@PathVariable("id") Long id, @RequestBody Moviments moviments) {
        moviments.setMovimientosid(id);
        Moviments movimentsDB = movimentsService.updateMoviments(moviments);
        if (movimentsDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimentsDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Moviments> deleteMoviments(@PathVariable("id") Long id) {
        Moviments movimentsDelete = movimentsService.deleteMoviments(id);
        if (movimentsDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimentsDelete);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
