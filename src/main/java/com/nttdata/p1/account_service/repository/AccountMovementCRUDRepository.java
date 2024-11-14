package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.AccountMovement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountMovementCRUDRepository extends ReactiveMongoRepository<AccountMovement, String> {
}
