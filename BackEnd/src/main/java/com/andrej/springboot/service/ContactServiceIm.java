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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
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
        Optional<ContactDAO> optionalContact = contactRepository.findById(id);

        if(optionalContact.isPresent()) {
            ContactDAO contactDAO = optionalContact.get();
            return ResponseEntity.ok(contactDAO);
        }
        else
            return new ResponseEntity<>("Contact with id: " + id + " was not found.", HttpStatus.NOT_FOUND);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name too short.");
        }

        if (!isValidNameFormat(dto.getLastName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name too short.");
        }

        if (!isValidEmailFormat(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format.");
        }

        if (!isValidNumber(dto.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format.");
        }

        if(dto.getAge() <= 15) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age less than 15.");
        }

        if(!isValidAddress(dto.getAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid address");
        }

        //Check whether the address exists
        if(addressRepository.existsByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                dto.getAddress().getCity(), dto.getAddress().getCountry(), dto.getAddress().getStreet(),
                dto.getAddress().getHouseNumber())) {
            AddressDAO existingAddress = addressRepository.findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber
                    (dto.getAddress().getCity(), dto.getAddress().getCountry(), dto.getAddress().getStreet(), dto.getAddress().getHouseNumber());

            dao.setAddress(existingAddress);
            dao.getAddress().setId(existingAddress.getId());
        }
        else {
            //Formatting and adding a new address
            dto.getAddress().setCountry(dto.getAddress().getCountry().toLowerCase());
            dto.getAddress().setCity(dto.getAddress().getCity().toLowerCase());
            dto.getAddress().setStreet(dto.getAddress().getStreet().toLowerCase());

            addressRepository.save(dto.getAddress());
            dao.setAddress(dto.getAddress());
        }

        dao.setFirstName(dto.getFirstName());
        dao.setLastName(dto.getLastName());
        dao.setAge(dto.getAge());
        dao.setEmail(dto.getEmail());
        dao.setPhoneNumber(dto.getPhoneNumber());

        contactRepository.save(dao);
        return ResponseEntity.ok(dao);
    }

    @Override
    public ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactDAO updateContact) {
        Optional<ContactDAO> optionalContact = contactRepository.findById(id);

        if(optionalContact.isPresent()) {
            ContactDAO contactDAO = new ContactDAO();

            if (!isValidNameFormat(updateContact.getFirstName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name too short.");
            }

            if (!isValidNameFormat(updateContact.getLastName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name too short.");
            }

            if (!isValidEmailFormat(updateContact.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format.");
            }

            if (!isValidNumber(updateContact.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format.");
            }

            if(updateContact.getAge() < 15) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age less than 15.");
            }

            if(!isValidAddress(updateContact.getAddress())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid address");
            }

            AddressDAO existingAddress = addressRepository.findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                    updateContact.getAddress().getCity(),
                    updateContact.getAddress().getCountry(),
                    updateContact.getAddress().getStreet(),
                    updateContact.getAddress().getHouseNumber()
            );

            if (existingAddress != null)
                updateContact.setAddress(existingAddress);
            else
                addressRepository.save(updateContact.getAddress());

            contactDAO.setAddress(updateContact.getAddress());
            contactDAO.setFirstName(updateContact.getFirstName());
            contactDAO.setLastName(updateContact.getLastName());
            contactDAO.setAge(updateContact.getAge());
            contactDAO.setEmail(updateContact.getEmail());
            contactDAO.setPhoneNumber(updateContact.getPhoneNumber());

            ContactDAO updatedContactDAO = contactRepository.save(contactDAO);
            return ResponseEntity.ok(updatedContactDAO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found");
        }
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
           address.getHouseNumber() < 1)
            return false;

        return true;
    }
}
