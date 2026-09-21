package com.contactmanager.infrastructure.adapters.out.database.repository;

import com.contactmanager.infrastructure.adapters.out.database.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    Optional<UserEntity> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
}