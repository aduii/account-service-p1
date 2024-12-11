package com.nttdata.p1.account_service.model;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String name;
    private String email;
    private String type;
}
