package com.andrej.springboot.repository;

import com.andrej.springboot.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findContactByEmailAndAge(String email, int age); //My own query example
}
