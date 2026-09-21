package com.contactmanager.infrastructure.adapters.in.rest.controller.mapper;

import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.LoginRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.UserRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserRequest request);

    User loginToDomain(LoginRequest request);


    UserResponse toResponse(User user);
}
