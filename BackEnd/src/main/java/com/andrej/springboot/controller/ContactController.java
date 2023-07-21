package com.andrej.springboot.controller;

import java.util.List;
import com.andrej.springboot.exception.ResourceNotFoundException;
import com.andrej.springboot.model.Contact;
import com.andrej.springboot.repository.ContactRepository;
import com.andrej.springboot.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("http://localhost:4200/")
public class ContactController {

    @Autowired
    private final ContactService service;

    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable long id) {
        return service.getContactById(id);
    }

    @PostMapping("/contacts")
    public Contact saveContact(@RequestBody Contact contact) {
        return service.saveContact(contact);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable long id, @RequestBody Contact contactDetails) {
        return service.updateContact(id, contactDetails);
    }

    @DeleteMapping("contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        return service.deleteContact(id);
    }
}
