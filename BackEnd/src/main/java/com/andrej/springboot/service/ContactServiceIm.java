package com.andrej.springboot.service;

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
            return ResponseEntity.status(HttpStatus.OK).body(contactDAO);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact with id: " + id + " was not found.");
    }

    @Override
    public ResponseEntity<List<ContactDAO>> getAllContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(contactRepository.findAll());
    }

    @Override
    public ResponseEntity<?> saveContact(@RequestBody ContactDTO dto) {
        ContactDAO dao = new ContactDAO();

        //Check for the logical and format errors
        if (!isValidNameFormat(dto.getFirstName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name too short!");
        }

        if (!isValidNameFormat(dto.getLastName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name too short!");
        }

        if (!isValidEmailFormat(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format!");
        }

        if (!isValidNumber(dto.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format!");
        }

        if(dto.getAge() <= 15) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age less than 15!");
        }

        if(!isValidAddress(dto.getAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid address!");
        }

        if(contactRepository.findContactDAOByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already used!");
        }

        //Check whether the address exists
        if(addressRepository.existsByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                dto.getAddress().getCity(),
                dto.getAddress().getCountry(),
                dto.getAddress().getStreet(),
                dto.getAddress().getHouseNumber())) {

            AddressDAO existingAddress = addressRepository.findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                    dto.getAddress().getCity(), dto.getAddress().getCountry(), dto.getAddress().getStreet(),
                    dto.getAddress().getHouseNumber());

            dao.setAddress(existingAddress);
            dao.getAddress().setId(existingAddress.getId());
        } else {
            //Formatting and adding a new address
            dto.getAddress().setCountry(dto.getAddress().getCountry().substring(0,1).toUpperCase() +
                    dto.getAddress().getCountry().substring(1).toLowerCase());
            dto.getAddress().setCity(dto.getAddress().getCity().substring(0,1).toUpperCase() +
                    dto.getAddress().getCity().substring(1).toLowerCase());
            dto.getAddress().setStreet(dto.getAddress().getStreet().substring(0,1).toUpperCase() +
                    dto.getAddress().getStreet().substring(1).toLowerCase());

            addressRepository.save(dto.getAddress());
            dao.setAddress(dto.getAddress());
        }

        dao.setFirstName(dto.getFirstName().substring(0,1).toUpperCase() + dto.getFirstName().substring(1).toLowerCase());
        dao.setLastName(dto.getLastName().substring(0,1).toUpperCase() + dto.getLastName().substring(1).toLowerCase());
        dao.setAge(dto.getAge());
        dao.setEmail(dto.getEmail().toLowerCase());
        dao.setPhoneNumber(dto.getPhoneNumber());

        contactRepository.save(dao);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact successfully added to the database.");
    }

    @Override
    public ResponseEntity<?> updateContact(@PathVariable long id, @RequestBody ContactDAO updateContact) {
        // Check the validness of the updateContact
        if (!isValidNameFormat(updateContact.getFirstName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name too short!");
        }

        if (!isValidNameFormat(updateContact.getLastName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name too short!");
        }

        if (!isValidEmailFormat(updateContact.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format!");
        }

        if (!isValidNumber(updateContact.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format!");
        }

        if(updateContact.getAge() < 15) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age less than 15!");
        }

        if(!isValidAddress(updateContact.getAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid address!");
        }

        Optional<ContactDAO> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            ContactDAO dao = optionalContact.get();

            // Check whether the address exists
            if (addressRepository.existsByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                    updateContact.getAddress().getCity(), updateContact.getAddress().getCountry(),
                    updateContact.getAddress().getStreet(), updateContact.getAddress().getHouseNumber())) {

                AddressDAO existingAddress = addressRepository.findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(
                        updateContact.getAddress().getCity(), updateContact.getAddress().getCountry(),
                        updateContact.getAddress().getStreet(), updateContact.getAddress().getHouseNumber());

                dao.setAddress(existingAddress);
                dao.getAddress().setId(existingAddress.getId()); // Probably unnecessary
            } else {
                //Properly format the new address
                AddressDAO newAddress = new AddressDAO();
                newAddress.setCountry(updateContact.getAddress().getCountry().substring(0,1).toUpperCase() +
                        updateContact.getAddress().getCountry().substring(1).toLowerCase());
                newAddress.setCity(updateContact.getAddress().getCity().substring(0,1).toUpperCase() +
                        updateContact.getAddress().getCity().substring(1).toLowerCase());
                newAddress.setStreet(updateContact.getAddress().getStreet().substring(0,1).toUpperCase() +
                        updateContact.getAddress().getStreet().substring(1).toLowerCase());
                newAddress.setHouseNumber(updateContact.getAddress().getHouseNumber());

                addressRepository.save(newAddress); //Add to the address repository
                dao.setAddress(newAddress);
            }

            dao.setFirstName(updateContact.getFirstName().substring(0,1).toUpperCase() +
                    updateContact.getFirstName().substring(1).toLowerCase());
            dao.setLastName(updateContact.getLastName().substring(0,1).toUpperCase() +
                    updateContact.getLastName().substring(1).toLowerCase());
            dao.setPhoneNumber(updateContact.getPhoneNumber());
            dao.setEmail(updateContact.getEmail().toLowerCase());
            dao.setAge(updateContact.getAge());
            contactRepository.save(dao);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found, therefore not updated.");
        }
    }

    @Override
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        Optional<ContactDAO> optional = contactRepository.findById(id);
        if(optional.isPresent()) {
            ContactDAO del = optional.get();
            contactRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the object.");
        }
    }

    private boolean isValidEmailFormat(String email) {
        return email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
    }

    private boolean isValidNameFormat(String name) {
        return name.length() >= 2;
    }

    /**
     * This method validates the formatting of an email address
     * @param number -> phone number to check
     * @return true -> correct Slovak formatting, false -> incorrect Slovak formatting
     */
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
      return address.getCountry().length() >= 4 && //Chad
              address.getCity().length() >= 3 && // Goa
              address.getStreet().length() >= 3 && // estimate
              address.getHouseNumber() >= 1;
    }

    public static String capitalize(String txt) {
        if (txt == null || txt.isEmpty()) {
            return txt;
        }

        return txt.substring(0, 1).toUpperCase() + txt.substring(1);
    }
}
