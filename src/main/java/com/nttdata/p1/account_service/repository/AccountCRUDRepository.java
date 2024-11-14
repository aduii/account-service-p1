package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountCRUDRepository extends ReactiveMongoRepository<Account, String> {
}
