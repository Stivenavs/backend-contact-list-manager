package com.contactmanager.infrastructure.adapters.out.database;

import com.contactmanager.application.exceptions.DuplicateEmailException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import com.contactmanager.domain.contactmanager.Contact;
import com.contactmanager.infrastructure.adapters.out.database.entities.ContactEntity;
import com.contactmanager.infrastructure.adapters.out.database.mapper.ContactEntityMapper;
import com.contactmanager.infrastructure.adapters.out.database.repository.ContactRepository;
import com.contactmanager.infrastructure.ports.out.ContactPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactAdapter implements ContactPort {

    private final ContactRepository jpaRepository;
    private final ContactEntityMapper mapper;
    
    @Override
    public Contact save(Contact contact) {
        try {
            ContactEntity saved = jpaRepository.saveAndFlush(mapper.toEntity(contact));
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException(ContactManagerErrorCodes.DUPLICATE_EMAIL, contact.getEmail());
        }
    }

    @Override
    public Optional<Contact> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Contact> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Integer id) {
        return jpaRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
