package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountCRUDRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByCustomerIdAndAccountType(String customerId, String type);
}
