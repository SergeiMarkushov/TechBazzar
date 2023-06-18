package ru.bazzar.core.configs;

public enum GlobalEnum {
    ADMIN_EMAIL("n.v.bekhter@mail.ru");

    private final String value;

    GlobalEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
