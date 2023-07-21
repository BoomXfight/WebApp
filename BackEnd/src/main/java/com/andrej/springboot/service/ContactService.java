package com.andrej.springboot.service;

import com.andrej.springboot.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ContactService {

    public ResponseEntity<Contact> getContactById(@PathVariable long id);

    public List<Contact> getAllContacts();

    public Contact saveContact(@RequestBody Contact contact);

    public ResponseEntity<Contact> updateContact(@PathVariable long id, @RequestBody Contact contactDetails);

    public ResponseEntity<?> deleteContact(@PathVariable long id);
}
