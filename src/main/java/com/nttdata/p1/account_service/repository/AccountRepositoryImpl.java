package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountCRUDRepository accountCrudRepository;

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountCrudRepository.save(account);
    }

    @Override
    public Flux<Account> getAllAccounts() {
        return accountCrudRepository.findAll();
    }

    @Override
    public Mono<Account> findByAccountId(String id) {
        return accountCrudRepository.findById(id);
    }

    @Override
    public Mono<Account> updateAccount(String id, Account account) {
        return accountCrudRepository.findById(id)
                .flatMap(existingAccount->{
                    existingAccount.setBalance(account.getBalance());
                    existingAccount.setMovementLimit(account.getMovementLimit());
                    existingAccount.setCommission(account.getCommission());
                    return accountCrudRepository.save(existingAccount);
                });
    }

    @Override
    public Mono<Void> deleteAccount(String id) {
        return accountCrudRepository.deleteById(id);
    }
}
