package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class DuplicateEmailException extends ApiException {
    public DuplicateEmailException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
