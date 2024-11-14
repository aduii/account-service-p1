package com.nttdata.p1.account_service.controller;

import com.nttdata.p1.account_service.model.AccountMovement;
import com.nttdata.p1.account_service.service.AccountMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
@RequestMapping("api/v1/account-movement")
public class AccountMovementController {
    @Autowired
    private AccountMovementService accountMovementService;

    @PostMapping
    public Mono<AccountMovement> createAccountMovement(@RequestBody AccountMovement accountMovement){
        return accountMovementService.createAccountMovement(accountMovement);
    }

    @GetMapping
    public Flux<AccountMovement> getAllAccountMovements(){
        return accountMovementService.getAllAccountMovements();
    }

    @GetMapping("/{id}")
    public Mono<AccountMovement> findByAccountMovementId(@PathVariable String id){
        return accountMovementService.findByAccountMovementId(id);
    }

    @PutMapping("/{id}")
    public Mono<AccountMovement> updateAccountMovement(
            @PathVariable String id,
            @RequestBody AccountMovement accountMovement){
        return accountMovementService.updateAccountMovement(id, accountMovement);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccountMovement(@PathVariable String id){
        return accountMovementService.deleteAccountMovement(id);
    }
}
