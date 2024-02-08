package org.example.curd.JsonServer;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {
    private UUID id;
    private String message;

    public Product() {
        this.id = UUID.randomUUID();
    }

    public Product(String message) {
        this.id = UUID.randomUUID();
        this.message = message;
    }
}
