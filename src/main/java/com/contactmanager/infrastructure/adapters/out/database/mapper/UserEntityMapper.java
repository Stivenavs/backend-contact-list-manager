package com.contactmanager.infrastructure.adapters.out.database.mapper;

import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.adapters.out.database.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}
