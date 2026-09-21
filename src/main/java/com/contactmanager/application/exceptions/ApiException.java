package com.contactmanager.application.exceptions;

import com.contactmanager.application.exceptions.enums.ApiErrorCode;

public class ApiException extends RuntimeException {
    private final ApiErrorCode code;
    private final String detail;

    protected ApiException(final ApiErrorCode code, String detail) {
        super(code.getLocalizedMessage());
        this.code = code;
        this.detail = detail;
    }

    public ApiErrorCode getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }
}
