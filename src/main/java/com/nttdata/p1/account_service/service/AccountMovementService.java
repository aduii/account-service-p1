package com.nttdata.p1.account_service.service;

import com.nttdata.p1.account_service.model.AccountMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountMovementService {
    Mono<AccountMovement> createAccountMovement(AccountMovement accountMovement);
    Flux<AccountMovement> getAllAccountMovements();
    Mono<AccountMovement> findByAccountMovementId(String id);
    Mono<AccountMovement> updateAccountMovement(String id, AccountMovement accountMovement);
    Mono<Void> deleteAccountMovement(String id);
}
