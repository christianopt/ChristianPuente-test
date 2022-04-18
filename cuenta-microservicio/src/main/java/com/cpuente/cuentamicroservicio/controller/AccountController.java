package com.cpuente.cuentamicroservicio.controller;

import com.cpuente.cuentamicroservicio.entity.Account;
import com.cpuente.cuentamicroservicio.service.AccountService;
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
@RequestMapping("/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> listAccount() {
        List<Account> accounts = new ArrayList<>();
        accounts = accountService.listAllAccount();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        Account account = accountService.getAccount(id);
        if (null == account) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Account accountCreate = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Account account) {
        account.setCuentaid(id);
        Account accountDB = accountService.updateAccount(account);
        if (accountDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Long id) {
        Account accountDelete = accountService.deleteAccount(id);
        if (accountDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountDelete);
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

