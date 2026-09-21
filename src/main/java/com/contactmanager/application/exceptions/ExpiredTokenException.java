package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class ExpiredTokenException extends ApiException {

    public ExpiredTokenException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
