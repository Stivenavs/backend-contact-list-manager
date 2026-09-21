package com.contactmanager.application.usecase.contactmanager;

import com.contactmanager.application.exceptions.ContactNotFoundException;
import com.contactmanager.application.exceptions.DuplicateEmailException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import com.contactmanager.domain.contactmanager.Contact;
import com.contactmanager.infrastructure.ports.in.ContactUseCase;
import com.contactmanager.infrastructure.ports.out.ContactPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ContactUseCaseImpl implements ContactUseCase {

    private final ContactPort contactPort;

    @Override
    public Contact create(Contact contact) {
        normalize(contact);

        if (contactPort.existsByEmail(contact.getEmail())) {
            throw new DuplicateEmailException(ContactManagerErrorCodes.DUPLICATE_EMAIL, contact.getEmail());
        }
        return contactPort.save(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public Contact getById(Integer id) {
        return contactPort.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(ContactManagerErrorCodes.CONTACT_NOT_FOUND, id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        return contactPort.findAll();
    }

    @Override
    public Contact update(Integer id, Contact contact) {
        Contact existing = getById(id);
        normalize(contact);

        existing.setFirstName(contact.getFirstName());
        existing.setLastName(contact.getLastName());
        existing.setPhone(contact.getPhone());
        existing.setEmail(contact.getEmail());

        return contactPort.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!contactPort.existsById(id)) {
            throw new ContactNotFoundException(ContactManagerErrorCodes.CONTACT_NOT_FOUND, id.toString());
        }
        contactPort.deleteById(id);
    }

    private void normalize(Contact contact) {
        contact.setFirstName(contact.getFirstName().trim());
        contact.setLastName(trimOrNull(contact.getLastName()));
        contact.setPhone(trimOrNull(contact.getPhone()));
        contact.setEmail(contact.getEmail().trim().toLowerCase());
    }

    private String trimOrNull(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}
