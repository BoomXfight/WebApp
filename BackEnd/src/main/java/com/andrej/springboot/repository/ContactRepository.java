package com.andrej.springboot.repository;

import com.andrej.springboot.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    Optional<ContactEntity> findContactEntityByEmail(String email);
}
