package com.andrej.springboot.model.dto;

import com.andrej.springboot.model.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactDTO {

    private long id;

    private byte age;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private AddressEntity address;
}
