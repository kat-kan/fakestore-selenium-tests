package com.github.katkan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    CARD("Karta debetowa/kredytowa (Stripe)");

    private final String description;

}

