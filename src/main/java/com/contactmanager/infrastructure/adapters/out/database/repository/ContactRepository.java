package com.contactmanager.infrastructure.adapters.out.database.repository;

import com.contactmanager.infrastructure.adapters.out.database.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
