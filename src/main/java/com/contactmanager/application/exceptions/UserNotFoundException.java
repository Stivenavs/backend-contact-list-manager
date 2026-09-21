package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
