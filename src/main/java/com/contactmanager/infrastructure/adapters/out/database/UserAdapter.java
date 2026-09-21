package com.contactmanager.infrastructure.adapters.out.database;

import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import com.contactmanager.domain.contactmanager.User;
import com.contactmanager.infrastructure.adapters.out.database.entities.UserEntity;
import com.contactmanager.infrastructure.adapters.out.database.mapper.UserEntityMapper;
import com.contactmanager.infrastructure.adapters.out.database.repository.UserRepository;
import com.contactmanager.infrastructure.ports.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

    private final UserRepository repository;
    private final UserEntityMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserAdapter.class);

    @Override
    public User save(User user) {
        UserEntity userEntity = mapper.toEntity(user);
        UserEntity userEntityCreated = repository.save(userEntity);

        return mapper.toDomain(userEntityCreated);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> optionalUserEntity = repository.findByEmailIgnoreCase(email);

        if(optionalUserEntity.isEmpty()){
            throw new UserNotFoundException(ContactManagerErrorCodes.USER_NOT_FOUND, UserNotFoundException.class.getName());
        }

        return mapper.toDomain(optionalUserEntity.get());
    }

    @Override
    public User findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber) throws UserNotFoundException {
        Optional<UserEntity> optionalUserEntity = repository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);

        logger.info("doc type: " + documentType + "\n doc number:" + documentNumber);


        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException(ContactManagerErrorCodes.USER_NOT_FOUND, UserNotFoundException.class.getName());
        }
        logger.debug(optionalUserEntity.get().toString());
        return mapper.toDomain(optionalUserEntity.get());
    }
}
