package com.github.katkan.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD("Karta debetowa/kredytowa (Stripe)");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }
}

