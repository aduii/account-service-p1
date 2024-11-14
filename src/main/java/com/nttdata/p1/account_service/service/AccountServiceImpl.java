package com.nttdata.p1.account_service.service;

import com.nttdata.p1.account_service.model.Account;
import com.nttdata.p1.account_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    @Override
    public Flux<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public Mono<Account> findByAccountId(String id) {
        return accountRepository.findByAccountId(id);
    }

    @Override
    public Mono<Account> updateAccount(String id, Account account) {
        return accountRepository.updateAccount(id, account);
    }

    @Override
    public Mono<Void> deleteAccount(String id) {
        return accountRepository.deleteAccount(id);
    }
}
