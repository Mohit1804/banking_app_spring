package com.mohitproject.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohitproject.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
