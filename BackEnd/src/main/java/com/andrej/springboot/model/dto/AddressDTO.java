package com.andrej.springboot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {

    private Long id;

    private String country;

    private String city;

    private String street;

    private int houseNumber;
}
