package com.andrej.springboot.service;

import com.andrej.springboot.exception.InvalidEmailFormatException;
import com.andrej.springboot.exception.ResourceNotFoundException;
import com.andrej.springboot.model.dao.AddressDAO;
import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.AddressDTO;
import com.andrej.springboot.model.dto.ContactDTO;
import com.andrej.springboot.repository.AddressRepository;
import com.andrej.springboot.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

@Service
@RequiredArgsConstructor
public class ContactServiceIm implements ContactService {

    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<?> getContactById(long id) {
        Optional<ContactDAO> contactDAO = contactRepository.findById(id);
        if(contactDAO.isPresent()) {


        }
        else
        {
            return ResponseEntity.status(500).body(new R)
        }

        return ResponseEntity.ok(contactDAO);
    }

    @Override
    public List<ContactDAO> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public ResponseEntity<?> saveContact(@RequestBody ContactDTO dto) {
        ContactDAO dao = new ContactDAO();
        // AddressDAO

        //Check for the logical and format errors
        if (!isValidNameFormat(dto.getFirstName())) {
            return ResponseEntity.status(500).body(new RuntimeException("First name too short."));
        }

        if (!isValidNameFormat(dto.getLastName())) {
            return ResponseEntity.status(500).body(new RuntimeException("Last name too short."));
        }

        if (!isValidEmailFormat(dto.getEmail())) {
            return ResponseEntity.status(500).body(new RuntimeException("Invalid email format."));
        }

        if (!isValidNumber(dto.getPhoneNumber())) {
            return ResponseEntity.status(500).body(new RuntimeException("Invalid phone number format."));
        }

        if(dto.getAge() <= 15) {
            return ResponseEntity.status(500).body(new RuntimeException("Age less than 15."));
        }

        if(!isValidAddress(dto.getAddress())) {
            return ResponseEntity.status(500).body(new RuntimeException("Invalid address"));
        }

        AddressDAO existingAddress = addressRepository.findByCountryCityStreetAndHouseNumber(
                dto.getAddress().getCountry(),
                dto.getAddress().getCity(),
                dto.getAddress().getStreet(),
                dto.getAddress().getHouse_number()
        );

        if (existingAddress != null)
            dto.setAddress(existingAddress);
        else
            addressRepository.save(dto.getAddress());

        dao.setFirstName(dto.getFirstName());
        dao.setLastName(dto.getLastName());
        dao.setAge(dto.getAge());
        dao.setEmail(dto.getEmail());
        dao.setPhoneNumber(dto.getPhoneNumber());
        dao.setAddress(dto.getAddress());

        contactRepository.save(dao);
        return ResponseEntity.ok(dao);
    }

    @Override
    public ResponseEntity<ContactDAO> updateContact(@PathVariable long id, @RequestBody ContactDAO contactDAODetails) {
        ContactDAO contactDAO = contactRepository.findById(id);


        contactDAO.setAddress(contactDAODetails.getAddress());
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
        if(number.length() != 10)
            return false;

        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);

            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidAddress(AddressDAO address) {
        if(address.getCountry().length() < 4 || //Chad
           address.getCity().length() < 4 || // Goa
           address.getStreet().length() < 3 || // estimate
           address.getHouse_number() < 1)
            return false;

        return true;
    }
}
