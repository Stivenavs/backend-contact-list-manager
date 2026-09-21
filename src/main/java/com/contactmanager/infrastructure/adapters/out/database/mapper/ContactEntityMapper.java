package com.contactmanager.infrastructure.adapters.out.database.mapper;

import com.contactmanager.domain.contactmanager.Contact;
import com.contactmanager.infrastructure.adapters.out.database.entities.ContactEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactEntityMapper {

    Contact toDomain(ContactEntity contactEntity);

    ContactEntity toEntity(Contact contact);
}