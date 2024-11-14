package com.nttdata.p1.account_service.controller;

import com.nttdata.p1.account_service.model.Account;
import com.nttdata.p1.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Mono<Account> createAccount(@RequestBody Account account){
        return accountService.createAccount(account);
    }

    @GetMapping
    public Flux<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Mono<Account> findByAccountId(@PathVariable String id){
        return accountService.findByAccountId(id);
    }

    @PutMapping("/{id}")
    public Mono<Account> updateAccount(
            @PathVariable String id,
            @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccount(@PathVariable String id){
        return accountService.deleteAccount(id);
    }
}
