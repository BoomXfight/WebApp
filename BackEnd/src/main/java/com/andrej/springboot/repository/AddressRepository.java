package com.andrej.springboot.repository;

import com.andrej.springboot.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(String city, String country,
                                                                                            String street , int houseNumber);
    Boolean existsByCityIgnoreCaseAndCountryIgnoreCaseAndStreetIgnoreCaseAndHouseNumber(String city, String country,
                                                                                        String street , int houseNumber);
}
