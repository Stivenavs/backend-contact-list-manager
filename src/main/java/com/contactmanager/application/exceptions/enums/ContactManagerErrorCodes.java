package com.contactmanager.application.exceptions.enums;

public enum ContactManagerErrorCodes implements ApiErrorCode {
    LOGIN_NOT_FOUND("001", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.LOGIN_NOT_FOUND"),
    USER_NOT_FOUND("002", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.USER_NOT_FOUND"),
    PASSWORD_ERROR("003", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.PASSWORD_ERROR"),
    INVALID_PERMISSIONS("004", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.INVALID_PERMISSIONS"),
    EXPIRED_TOKEN("005", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.EXPIRED_TOKEN"),
    TOKEN_NOT_FOUND("006", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.TOKEN_NOT_FOUND"),
    INVALID_TOKEN("007", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.INVALID_TOKEN"),
    SMTP_CONFIG_NOT_FOUND("008", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.SMTP_CONFIG_NOT_FOUND"),
    RESOURCE_NOT_FOUND("009", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.RESOURCE_NOT_FOUND"),
    DUPLICATE_EMAIL("010", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.DUPLICATE_EMAIL"),
    CONTACT_NOT_FOUND("011", "com.contactmanager.application.exceptions.contactmanager.enums.ContactManagerErrorCodes.CONTACT_NOT_FOUND");


    private static final String PREFIX = "ERR";
    private final String code;
    private final String localizedMessage;

    private ContactManagerErrorCodes(String code, String localizedMessage) {
        this.code = code;
        this.localizedMessage = localizedMessage;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
