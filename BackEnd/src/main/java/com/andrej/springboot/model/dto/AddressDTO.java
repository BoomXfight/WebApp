package com.andrej.springboot.model.dto;

import com.andrej.springboot.model.dao.ContactDAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {

    private Long id;

    private String country;

    private String city;

    private String street;

    private int house_number;

    private List<ContactDTO> contacts;
}
