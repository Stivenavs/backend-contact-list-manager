package com.contactmanager.infrastructure.ports.in;

import com.contactmanager.domain.contactmanager.User;

public interface UserUserCase {
    User create(User user);

    User updatePassword(User user);
}
