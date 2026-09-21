package com.contactmanager.infrastructure.ports.out;

import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.domain.contactmanager.User;

public interface UserPort {
    User save(User user);

    User findByEmail(String email) throws UserNotFoundException;

    User findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber) throws UserNotFoundException;
}
