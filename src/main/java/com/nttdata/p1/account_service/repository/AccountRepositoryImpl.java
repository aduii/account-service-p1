package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.Account;
import com.nttdata.p1.account_service.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountCRUDRepository accountCrudRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Account> createAccount(Account account) {
        return webClient.get()
                .uri("/{id}", account.getCustomerId())
                .retrieve()
                .bodyToMono(Customer.class)
                .flatMap(customer -> {
                    // Validar restricciones según el tipo de cliente
                    if (customer.getType().equalsIgnoreCase("personal")) {
                        return validatePersonalCustomer(account, customer);
                    } else if (customer.getType().equalsIgnoreCase("empresarial")) {
                        return validateBusinessCustomer(account, customer);
                    } else {
                        return Mono.error(new RuntimeException("Tipo de cliente desconocido"));
                    }
                })
                .flatMap(validatedAccount -> {
                    // Guardar la cuenta si pasa la validación
//                    validatedAccount.setCreatedAt(LocalDateTime.now());
                    return accountCrudRepository.save(validatedAccount);
                });
    }

    private Mono<Account> validatePersonalCustomer(Account account, Customer customer) {
        return accountCrudRepository.findByCustomerIdAndAccountType(customer.getId(), account.getAccountType())
                .flatMap(existingAccount -> {
                    if (existingAccount != null) {
                        return Mono.error(new RuntimeException("El cliente ya tiene una cuenta de este tipo"));
                    }
                    return Mono.just(account);
                })
                .switchIfEmpty(Mono.just(account));
    }

    private Mono<Account> validateBusinessCustomer(Account account, Customer customer) {
        if (!account.getAccountType().equalsIgnoreCase("current")) {
            return Mono.error(new RuntimeException("Los clientes empresariales solo pueden tener cuentas corrientes"));
        }
        return Mono.just(account); // Sin límite para cuentas corrientes
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
