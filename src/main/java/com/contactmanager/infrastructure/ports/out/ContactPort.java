package com.contactmanager.infrastructure.ports.out;

import com.contactmanager.domain.contactmanager.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactPort {
    Contact save(Contact contact);

    Optional<Contact> findById(Integer id);

    List<Contact> findAll();

    boolean existsById(Integer id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);

    void deleteById(Integer id);
}
