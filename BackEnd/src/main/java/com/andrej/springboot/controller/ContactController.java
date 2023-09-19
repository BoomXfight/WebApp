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
@CrossOrigin("http://localhost:4200/")
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    /**
     * This endpoint is responsible for fetching all contacts from the database
     * @return Http response containing the list of all contacts in the database
     */
    @GetMapping("/contacts")
    @PreAuthorize("hasAnyRole('backend_user', 'backend_admin')")
    public ResponseEntity<List<ContactDAO>> getAllContacts() {
        return service.getAllContacts();
    }

    /**
     * This endpoint is responsible for fetching a contact with a specific identifier from the database
     * @param id -> Contact identifier
     * @return -> Http response with a single contact when the id is present in the database, or a NOT_FOUND response
     */
    @GetMapping("/contacts/{id}")
    @PreAuthorize("hasAnyRole('backend_admin')")
    public ResponseEntity<?> getContactById(@PathVariable long id) {
        return service.getContactById(id);
    }

    /**
     * This endpoint is responsible for saving a new contact into the database
     * @param contactDTO -> Contact to be saved in the database
     * @return -> Http response indicating the successful addition of contact to the database or an HTTP response with
     * an error message indicating which part of the contactDTO was incompatible
     */
    @PostMapping("/contacts")
    @PreAuthorize("hasAnyRole('backend_admin')")
    public ResponseEntity<?> saveContact(@RequestBody ContactDTO contactDTO) {
        return service.saveContact(contactDTO);
    }

    // TODO check whether the ContactDAO is used correctly and shouldn't be replaced with the ContactDTO

    /**
     * This endpoint is responsible for updating a contact in the database with a specific id
     * @param id -> Unique identifier of a contact
     * @param contactDAO -> Contact with new credentials to be updated
     * @return -> Http response indicating the successful modification of a contact or an HTTP response with an error
     * message indicating which part of the contactDAO was incompatible
     */
    @PutMapping("/contacts/{id}")
    @PreAuthorize("hasAnyRole('backend_admin')")
    public ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAO) {
        return service.updateContact(id, contactDAO);
    }

    /**
     * This endpoint is responsible for deleting a contact from the database with a specific id
     * @param id -> Unique identifier of a contact
     * @return -> HTTP response indicating the successful deletion of a contact or an HTTP response with an error message
     * indicating that the contact with id was not found in the database, therefore could not be deleted
     */
    @DeleteMapping("contacts/{id}")
    @PreAuthorize("hasAnyRole('backend_admin')")
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        return service.deleteContact(id);
    }
}
