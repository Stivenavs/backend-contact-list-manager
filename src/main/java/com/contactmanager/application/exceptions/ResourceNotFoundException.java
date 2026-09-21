package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(ApiErrorCode code, String detail) {
        super(code, detail);
    }
}
