package com.nttdata.p1.account_service.repository;

import com.nttdata.p1.account_service.model.Account;
import com.nttdata.p1.account_service.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.nttdata.p1.account_service.utils.parameters.*;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountCRUDRepository accountCrudRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Account> createAccount(Account account) {
        Map<String, Function<Customer, Mono<Account>>> customerValidators = Map.of(
                "personal", customer -> validatePersonalCustomer(account, customer),
                "empresarial", customer -> validateBusinessCustomer(account, customer)
        );

        return webClient.get()
                .uri("/{id}", account.getCustomerId())
                .retrieve()
                .bodyToMono(Customer.class)
                .flatMap(customer ->
                        Optional.ofNullable(customerValidators.get(customer.getType().toLowerCase()))
                                .map(validator -> validator.apply(customer))
                                .orElseGet(() -> Mono.error(new RuntimeException(CustomerValidatorGlobalError)))
                )
                .flatMap(validatedAccount -> accountCrudRepository.save(validatedAccount));
    }

    private Mono<Account> validatePersonalCustomer(Account account, Customer customer) {
        return accountCrudRepository.findByCustomerIdAndAccountType(customer.getId(), account.getAccountType())
                .filter(Objects::isNull) // Solo permite si no existe una cuenta previa
                .switchIfEmpty(Mono.error(new RuntimeException(PersonalCustomerValidatorError)))
                .then(Mono.just(account));
    }


    private Mono<Account> validateBusinessCustomer(Account account, Customer customer) {
        if (!account.getAccountType().equalsIgnoreCase("corriente")) {
            return Mono.error(new RuntimeException(BusinessCustomerValidatorError));
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
