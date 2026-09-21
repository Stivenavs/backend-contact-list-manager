package com.contactmanager.infrastructure.ports.in;

import com.contactmanager.application.exceptions.PasswordErrorException;
import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.domain.contactmanager.User;

public interface AuthetnticationUseCase {
    User login(User user) throws UserNotFoundException, PasswordErrorException;

    User loginByDocument(User user) throws UserNotFoundException, PasswordErrorException;
}
