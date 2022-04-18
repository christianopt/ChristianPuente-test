package com.cpuente.cuentamicroservicio.service;

import com.cpuente.cuentamicroservicio.entity.Account;

import java.util.List;

public interface AccountService {

    public List<Account> listAllAccount();
    public Account getAccount(Long id);
    public Account createAccount(Account account);
    public Account updateAccount(Account account);
    public Account deleteAccount(Long id);

    
}
