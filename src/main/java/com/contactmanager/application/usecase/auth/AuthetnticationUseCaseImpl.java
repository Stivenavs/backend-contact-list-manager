package com.contactmanager.application.usecase.auth;

import com.contactmanager.application.exceptions.PasswordErrorException;
import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.ports.in.AuthetnticationUseCase;
import com.contactmanager.infrastructure.ports.in.TokenUseCase;
import com.contactmanager.infrastructure.ports.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthetnticationUseCaseImpl implements AuthetnticationUseCase {

    private final UserPort userPort;
    private final TokenUseCase tokenUseCase;

    @Override
    public User login(User user) throws UserNotFoundException, PasswordErrorException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User userAuthenticated = userPort.findByEmail(user.getEmail());

        if( !passwordEncoder.matches(user.getPassword(), userAuthenticated.getPassword()) ) {
            throw new PasswordErrorException(ContactManagerErrorCodes.PASSWORD_ERROR, "");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", userAuthenticated.getUserId());
        map.put("FullName", userAuthenticated.getFirstName() + " " + userAuthenticated.getLastName());
        map.put("email", userAuthenticated.getEmail());

        userAuthenticated.setToken(tokenUseCase.generateToken(map));

        return userAuthenticated;
    }

    @Override
    public User loginByDocument(User user) throws UserNotFoundException, PasswordErrorException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User userAuthenticated = userPort.findByDocumentTypeAndDocumentNumber(
                user.getDocumentType(), user.getDocumentNumber());

        if( !passwordEncoder.matches(user.getPassword(), userAuthenticated.getPassword()) ) {
            throw new PasswordErrorException(ContactManagerErrorCodes.PASSWORD_ERROR, "");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", userAuthenticated.getUserId());
        map.put("FullName", userAuthenticated.getFirstName() + " " + userAuthenticated.getLastName());
        map.put("email", userAuthenticated.getEmail());

        userAuthenticated.setToken(tokenUseCase.generateToken(map));

        return userAuthenticated;
    }
}
