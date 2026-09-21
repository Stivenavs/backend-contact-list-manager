package com.contactmanager.infrastructure.config;

import com.contactmanager.application.exceptions.ApiException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handleApiException(ApiException ex) {

        String errorCode = ex.getCode().getPrefix() + "-" + ex.getCode().getCode();

        String message = messageSource.getMessage(
                ex.getCode().getLocalizedMessage(),
                null,
                LocaleContextHolder.getLocale()
        );

        Map<String, String> body = new HashMap<>();
        body.put("code", errorCode);
        body.put("message", message);
        body.put("detail", ex.getDetail());

        return ResponseEntity
                .status(resolveHttpStatus(ex))
                .body(body);
    }

    private HttpStatus resolveHttpStatus(ApiException ex) {
        return switch ((ContactManagerErrorCodes) ex.getCode()) {
            case INVALID_TOKEN, EXPIRED_TOKEN -> HttpStatus.UNAUTHORIZED;
            case INVALID_PERMISSIONS -> HttpStatus.FORBIDDEN;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case RESOURCE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
