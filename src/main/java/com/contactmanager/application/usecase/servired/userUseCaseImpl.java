package com.contactmanager.application.usecase.servired;

import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.ports.in.UserUserCase;
import com.contactmanager.infrastructure.ports.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class userUseCaseImpl implements UserUserCase {

    private final UserPort userPort;

    @Override
    public User create(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userPort.save(user);
    }

    @Override
    public User updatePassword(User user) {
        User userToUpdate = userPort.findByEmail(user.getEmail());

        userToUpdate.setPassword(user.getPassword());

        return create(userToUpdate);
    }

}
