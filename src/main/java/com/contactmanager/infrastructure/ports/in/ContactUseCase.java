package com.contactmanager.infrastructure.ports.in;

import com.contactmanager.domain.contactmanager.Contact;

import java.util.List;

public interface ContactUseCase {

    Contact create(Contact contact);

    Contact getById(Integer id);

    List<Contact> getAll();

    Contact update(Integer id, Contact contact);

    void delete(Integer id);
}
