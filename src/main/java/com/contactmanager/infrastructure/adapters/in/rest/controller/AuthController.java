package com.contactmanager.infrastructure.adapters.in.rest.controller;

import com.contactmanager.application.exceptions.PasswordErrorException;
import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.adapters.in.rest.configuration.AuthApi;
import com.contactmanager.infrastructure.adapters.in.rest.controller.mapper.UserMapper;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.LoginRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.UserResponse;
import com.contactmanager.infrastructure.ports.in.AuthetnticationUseCase;
import com.contactmanager.infrastructure.ports.in.UserUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${request-mapping.controller.auth}")
public class AuthController implements AuthApi {

    private final AuthetnticationUseCase authetnticationUseCase;
    private final UserMapper userMapper;
    private final UserUserCase userUserCase;

    @Override
    public ResponseEntity<UserResponse> login(LoginRequest loginRequest) throws UserNotFoundException, PasswordErrorException {
        User user = authetnticationUseCase.login(userMapper.loginToDomain(loginRequest));
        return new ResponseEntity<>(userMapper.toResponse(user), HttpStatus.OK);
    }

}
