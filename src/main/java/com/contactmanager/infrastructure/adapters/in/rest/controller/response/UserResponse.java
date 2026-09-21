package com.contactmanager.infrastructure.adapters.in.rest.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String status;
    private String avatarUrl;
    private String phone;
}