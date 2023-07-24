package com.andrej.springboot.service;

import com.andrej.springboot.exception.InvalidEmailFormatException;
import com.andrej.springboot.exception.InvalidNameFormatException;
import com.andrej.springboot.exception.InvalidPhoneNumberFormatException;
import com.andrej.springboot.exception.ResourceNotFoundException;
import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.ContactDTO;
import com.andrej.springboot.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceIm implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ResponseEntity<ContactDAO> getContactById(long id) {
        ContactDAO contactDAO = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        return ResponseEntity.ok(contactDAO);
    }

    @Override
    public List<ContactDAO> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public ContactDAO saveContact(@RequestBody ContactDTO contactDTO) {
        ContactDAO dao = new ContactDAO();

        //Check for the logical and format errors
        if (!isValidNameFormat(contactDTO.getFirstName()))
            throw new InvalidNameFormatException("Invalid first name format.");

        if (!isValidNameFormat(contactDTO.getLastName()))
            throw new InvalidNameFormatException("Invalid last name format.");

        if (!isValidEmailFormat(contactDTO.getEmail()))
            throw new InvalidEmailFormatException("Invalid email format.");

        if(!isValidNumber(contactDTO.getPhoneNumber()))
            throw new InvalidPhoneNumberFormatException("Invalid phone number format.");

        if(contactDTO.getAge() <= 1) // Question, do I need to make custom?
            throw new RuntimeException("Invalid age.");

        dao.setFirstName(contactDTO.getFirstName());
        dao.setLastName(contactDTO.getLastName());
        dao.setAge(contactDTO.getAge());
        dao.setEmail(contactDTO.getEmail());
        dao.setAddressDAO(contactDTO.getAddressDAO());
        dao.setPhoneNumber(contactDTO.getPhoneNumber());
        return contactRepository.save(dao);
    }

    @Override
    public ResponseEntity<ContactDAO> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAODetails) {
        ContactDAO contactDAO = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        contactDAO.setAddressDAO(contactDAODetails.getAddressDAO());
        contactDAO.setFirstName(contactDAODetails.getFirstName());
        contactDAO.setLastName(contactDAODetails.getLastName());
        contactDAO.setAge(contactDAODetails.getAge());
        contactDAO.setEmail(contactDAODetails.getEmail());
        contactDAO.setPhoneNumber(contactDAODetails.getPhoneNumber());

        ContactDAO updatedContactDAO = contactRepository.save(contactDAO);
        return ResponseEntity.ok(updatedContactDAO);
    }

    @Override
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        ContactDAO contactDAO = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + id + " does not exist."));

        contactRepository.delete(contactDAO);
        return ResponseEntity.ok("OK");
    }

    private boolean isValidEmailFormat(String email) {
        return email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
    }

    private boolean isValidNameFormat(String name) {
        return name.length() >= 2;
    }

    private boolean isValidNumber(String number) {
        String digitsPattern = "\\d{10}";
        return number.matches(digitsPattern);
    }
}
