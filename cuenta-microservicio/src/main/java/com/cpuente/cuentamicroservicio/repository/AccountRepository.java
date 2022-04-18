package com.cpuente.cuentamicroservicio.repository;

import com.cpuente.cuentamicroservicio.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
