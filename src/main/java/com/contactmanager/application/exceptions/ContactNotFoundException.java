package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class ContactNotFoundException extends ApiException {
    public ContactNotFoundException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
