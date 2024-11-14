package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.AccountMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class AccountMovementRepositoryImpl implements AccountMovementRepository {

    @Autowired
    private AccountMovementCRUDRepository accountMovementCRUDRepository;

    @Override
    public Mono<AccountMovement> createAccountMovement(AccountMovement accountMovement) {
        if (accountMovement.getDate() == null) {
            accountMovement.setDate(LocalDateTime.now());
        }
        return accountMovementCRUDRepository.save(accountMovement);
    }

    @Override
    public Flux<AccountMovement> getAllAccountMovements() {
        return accountMovementCRUDRepository.findAll();
    }

    @Override
    public Mono<AccountMovement> findByAccountMovementId(String id) {
        return accountMovementCRUDRepository.findById(id);
    }

    @Override
    public Mono<AccountMovement> updateAccountMovement(String id, AccountMovement accountMovement) {
        return accountMovementCRUDRepository.findById(id)
                .flatMap(existing->{
                    existing.setAmount(accountMovement.getAmount());
                    existing.setType(accountMovement.getType());
                    existing.setDate(accountMovement.getDate() != null ? accountMovement.getDate() : LocalDateTime.now());
                    return accountMovementCRUDRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteAccountMovement(String id) {
        return accountMovementCRUDRepository.deleteById(id);
    }
}
