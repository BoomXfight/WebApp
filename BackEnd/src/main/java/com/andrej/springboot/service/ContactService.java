package com.andrej.springboot.service;

import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.ContactDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ContactService {

    public ResponseEntity<?> getContactById(@PathVariable long id);

    public ResponseEntity<List<ContactDAO>> getAllContacts();

    public ResponseEntity<?> saveContact(@RequestBody ContactDTO contactDTO);

    public ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAODetails);

    public ResponseEntity<?> deleteContact(@PathVariable long id);
}
