package com.andrej.springboot.repository;

import com.andrej.springboot.model.ContactDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactDAO, Long> {

    Optional<ContactDAO> findContactByEmailAndAge(String email, int age); //My own query example
}
