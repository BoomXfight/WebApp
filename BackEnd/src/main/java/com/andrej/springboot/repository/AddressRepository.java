package com.andrej.springboot.repository;

import com.andrej.springboot.model.dao.AddressDAO;
import com.andrej.springboot.model.dao.ContactDAO;
import com.andrej.springboot.model.dto.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDAO, Long> {
//    @Query("SELECT a FROM AddressDAO a WHERE a.country = ?1 AND a.city = ?2 AND a.street = ?3 AND a.house_number = ?4")
//    AddressDAO findByCountryCityStreetAndHouseNumber(String country, String city, String street, int house_number);
    AddressDAO findByCityAndCountryAndStreetAndHouseNumber(String city, String country, String street , int houseNumber);

    Boolean existsByCityAndAndCountryAndStreetAndHouseNumber(String city, String country, String street , int houseNumber);
}
