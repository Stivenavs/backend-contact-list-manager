package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class PasswordErrorException extends ApiException {

    public PasswordErrorException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
