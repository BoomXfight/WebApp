package com.andrej.springboot.service;

import com.andrej.springboot.exception.ResourceNotFoundException;
import com.andrej.springboot.model.Contact;
import com.andrej.springboot.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceIm implements ContactService {

    @Autowired
    private final ContactRepository contactRepository;

    @Override
    public ResponseEntity<Contact> getContactById(long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        return ResponseEntity.ok(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact saveContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public ResponseEntity<Contact> updateContact(@PathVariable long id, @RequestBody Contact contactDetails) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        contact.setAddress(contactDetails.getAddress());
        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setAge(contactDetails.getAge());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhoneNumber(contactDetails.getPhoneNumber());

        Contact updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @Override
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        contactRepository.delete(contact);
        return ResponseEntity.ok("OK");
    }
}
