package com.andrej.springboot.controller;

import java.util.List;

import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.ContactDTO;
import com.andrej.springboot.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("http://localhost:4200/")
public class ContactController {

    //private final ContactService service;
    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/contacts")
    public List<ContactDAO> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactDAO> getContactById(@PathVariable long id) {
        return service.getContactById(id);
    }

    @PostMapping("/contacts")
    public ContactDAO saveContact(@RequestBody ContactDTO contactDTO) {
        return service.saveContact(contactDTO);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactDAO> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAODetails) {
        return service.updateContact(id, contactDAODetails);
    }

    @DeleteMapping("contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        return service.deleteContact(id);
    }
}
