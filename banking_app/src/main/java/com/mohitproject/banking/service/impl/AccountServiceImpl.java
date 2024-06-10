package com.mohitproject.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mohitproject.banking.dto.AccountDto;
import com.mohitproject.banking.entity.Account;
import com.mohitproject.banking.mapper.AccountMapper;
import com.mohitproject.banking.repository.AccountRepository;
import com.mohitproject.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist."));
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double ammount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist."));
		double total = account.getBalance() + ammount;
		account.setBalance(total);
		Account savedaccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedaccount);
	}

	@Override
	public AccountDto withdraw(Long id, double ammount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist."));

		if (account.getBalance() < ammount) {
			throw new RuntimeException("Insufficient Amount");
		}
		double total = account.getBalance() - ammount;
		account.setBalance(total);
		Account savedaccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedaccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist."));

		accountRepository.deleteById(id);

	}

}
