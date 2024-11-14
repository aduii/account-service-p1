package com.nttdata.p1.account_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "account_movements")
public class AccountMovement {
    @Id
    private String id;
    private String accountId;
    private Double amount;
    private String type; // Deposito o Retiro
    private LocalDateTime date;
}
