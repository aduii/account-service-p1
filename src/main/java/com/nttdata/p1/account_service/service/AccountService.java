package com.nttdata.p1.account_service.service;

import com.nttdata.p1.account_service.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> createAccount(Account account);
    Flux<Account> getAllAccounts();
    Mono<Account> findByAccountId(String id);
    Mono<Account> updateAccount(String id, Account account);
    Mono<Void> deleteAccount(String id);
}
