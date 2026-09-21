package com.contactmanager.domain.contactmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private String documentType;

    private String documentNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private String password;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String token;

    private String avatarUrl;
}
