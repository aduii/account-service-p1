package com.nttdata.p1.account_service.service;

import com.nttdata.p1.account_service.model.AccountMovement;
import com.nttdata.p1.account_service.repository.AccountMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountMovementServiceImpl implements AccountMovementService {

    @Autowired
    private AccountMovementRepository accountMovementRepository;

    @Override
    public Mono<AccountMovement> createAccountMovement(AccountMovement accountMovement) {
        return accountMovementRepository.createAccountMovement(accountMovement);
    }

    @Override
    public Flux<AccountMovement> getAllAccountMovements() {
        return accountMovementRepository.getAllAccountMovements();
    }

    @Override
    public Mono<AccountMovement> findByAccountMovementId(String id) {
        return accountMovementRepository.findByAccountMovementId(id);
    }

    @Override
    public Mono<AccountMovement> updateAccountMovement(String id, AccountMovement accountMovement) {
        return accountMovementRepository.updateAccountMovement(id, accountMovement);
    }

    @Override
    public Mono<Void> deleteAccountMovement(String id) {
        return accountMovementRepository.deleteAccountMovement(id);
    }
}
