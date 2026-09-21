package com.contactmanager.application.exceptions.enums;

import java.io.Serializable;

public interface ApiErrorCode extends Serializable {
    String getPrefix();

    String getCode();

    String getLocalizedMessage();
}
