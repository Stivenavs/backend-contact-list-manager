package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class InvalidTokenException extends ApiException {

    public InvalidTokenException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
