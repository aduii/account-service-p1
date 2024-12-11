package com.nttdata.p1.account_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private String accountType; // Ahorro, Corriente, Plazo Fijo
    private Double balance;
    private String customerId; // Cliente asociado
    private Double movementLimit;
    private Double commission;
}
