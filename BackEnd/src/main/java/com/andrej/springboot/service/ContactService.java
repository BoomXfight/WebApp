package com.andrej.springboot.service;

import com.andrej.springboot.model.entity.ContactEntity;
import com.andrej.springboot.model.dto.ContactDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * This interface represents methods necessary for manipulation with contacts in the database
 */
public interface ContactService {

    ResponseEntity<List<ContactEntity>> getAllContacts();

    ResponseEntity<?> getContactById(@PathVariable long id);

    ResponseEntity<?> saveContact(@RequestBody ContactDTO contactDTO);

    ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactEntity contactEntityDetails);

    ResponseEntity<?> deleteContact(@PathVariable long id);
}
