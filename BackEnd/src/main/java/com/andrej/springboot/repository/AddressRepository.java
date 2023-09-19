package com.andrej.springboot.repository;

import com.andrej.springboot.model.dao.AddressDAO;
import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDAO, Long> {
    AddressDAO findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(String city, String country, String street , int houseNumber);
    Boolean existsByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(String city, String country, String street , int houseNumber);
}
