package com.cpuente.clientemicroservicio.controller;

import com.cpuente.clientemicroservicio.entity.Client;
import com.cpuente.clientemicroservicio.service.ClientService;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> listClient(@RequestParam(name = "estado", required = false) String estado){
        List<Client> clients = new ArrayList<>();



        if (null ==  estado){
            clients = clientService.listAllClient();
            if (clients.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            clients = clientService.findByEstado(estado);
            if (clients.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }



        return ResponseEntity.ok(clients);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        Client client =  clientService.getClient(id);
        if (null==client){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Client clientCreate =  clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @RequestBody Client client){
        client.setPersonaid(id);
        Client clientDB =  clientService.updateClient(client);
        if (clientDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id){
        Client clientDelete = clientService.deleteClient(id);
        if (clientDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDelete);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
