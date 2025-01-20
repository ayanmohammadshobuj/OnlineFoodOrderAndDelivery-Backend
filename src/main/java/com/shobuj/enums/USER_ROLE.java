package com.shobuj.enums;

public enum USER_ROLE {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    RESTAURANT("Restaurant"),
    RIDER("Rider");

    private String value;

    USER_ROLE(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
