package com.contactmanager.infrastructure.adapters.in.rest.controller;

import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.adapters.in.rest.configuration.UserApi;
import com.contactmanager.infrastructure.adapters.in.rest.controller.mapper.UserMapper;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.UserRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.UserResponse;
import com.contactmanager.infrastructure.ports.in.UserUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${request-mapping.controller.users}")
public class UserController implements UserApi {

    private final UserUserCase userUserCase;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserResponse> create(UserRequest userRequest){
        User user = userUserCase.create(userMapper.toDomain(userRequest));
        return new ResponseEntity<>(userMapper.toResponse(user), HttpStatus.OK);
    }
}
