package com.contactmanager.infrastructure.adapters.in.rest.controller.mapper;

import com.contactmanager.domain.contactmanager.Contact;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.ContactRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

//    @Mapping(target = "contactId", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    Contact toDomain(ContactRequest request);

    ContactResponse toResponse(Contact contact);
}
