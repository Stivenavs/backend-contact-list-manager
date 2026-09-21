package com.contactmanager.infrastructure.adapters.in.rest.controller;

import com.contactmanager.domain.contactmanager.Contact;
import com.contactmanager.infrastructure.adapters.in.rest.configuration.ContactApi;
import com.contactmanager.infrastructure.adapters.in.rest.controller.mapper.ContactMapper;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.ContactRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.ContactResponse;
import com.contactmanager.infrastructure.ports.in.ContactUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController implements ContactApi {

    private final ContactUseCase contactUseCase;
    private final ContactMapper contactMapper;

    @Override
    public ResponseEntity<ContactResponse> create(ContactRequest contactRequest) {
        Contact created = contactUseCase.create(contactMapper.toDomain(contactRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(contactMapper.toResponse(created));
    }

    @Override
    public ResponseEntity<List<ContactResponse>> findAll() {
        List<ContactResponse> contacts = contactUseCase.getAll().stream()
                .map(contactMapper::toResponse)
                .toList();
        return ResponseEntity.ok(contacts);
    }

    @Override
    public ResponseEntity<ContactResponse> findById(Integer id) {
        return ResponseEntity.ok(contactMapper.toResponse(contactUseCase.getById(id)));
    }

    @Override
    public ResponseEntity<ContactResponse> update(Integer id, ContactRequest contactRequest) {
        Contact updated = contactUseCase.update(id, contactMapper.toDomain(contactRequest));
        return ResponseEntity.ok(contactMapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        contactUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
