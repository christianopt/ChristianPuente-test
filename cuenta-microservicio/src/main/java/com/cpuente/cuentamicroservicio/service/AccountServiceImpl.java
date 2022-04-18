package com.cpuente.cuentamicroservicio.service;

import com.cpuente.cuentamicroservicio.entity.Account;
import com.cpuente.cuentamicroservicio.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createAccount(Account account) {
        account.setEstado("True");
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        Account accountDB = getAccount(account.getCuentaid());
        if(null== accountDB){
            return null;
        }
        accountDB.setNumerocuenta(account.getNumerocuenta());
        accountDB.setTipocuenta(account.getTipocuenta());
        accountDB.setSaldoinicial(account.getSaldoinicial());
        accountDB.setClienteid(account.getClienteid());
        accountDB.setEstado(account.getEstado());
        return accountRepository.save(accountDB);
    }

    @Override
    public Account deleteAccount(Long id) {
        Account accountDB = getAccount(id);
        if(null== accountDB){
            return null;
        }
        accountDB.setEstado("False");
        return accountRepository.save(accountDB);
    }
}
