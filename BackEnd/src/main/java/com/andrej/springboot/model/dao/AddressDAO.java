package com.andrej.springboot.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    private String city;

    private String street;

    private int house_number;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactDAO> contacts;
}
