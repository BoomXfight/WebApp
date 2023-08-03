package com.andrej.springboot.controller;

import java.util.List;

import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.ContactDTO;
import com.andrej.springboot.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@CrossOrigin("http://localhost:4200/")
public class ContactController {

    //private final ContactService service;
    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/contacts")
    @PreAuthorize("hasAuthority('contacts:read')")
    public ResponseEntity<List<ContactDAO>> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/contacts/{id}")
    @PreAuthorize("hasAuthority('contact:read1')")
    public ResponseEntity<?> getContactById(@PathVariable long id) {
        return service.getContactById(id);
    }

    @PostMapping("/contacts")
    @PreAuthorize("hasAuthority('contact:create')")
    public ResponseEntity<?> saveContact(@RequestBody ContactDTO contactDTO) {
        return service.saveContact(contactDTO);
    }

    @PutMapping("/contacts/{id}")
    @PreAuthorize("hasAuthority('contact:update')")
    public ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAODetails) {
        return service.updateContact(id, contactDAODetails);
    }

    @DeleteMapping("contacts/{id}")
    @PreAuthorize("hasAuthority('contact:delete')")
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        return service.deleteContact(id);
    }
}
