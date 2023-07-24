package com.andrej.springboot.model.dao;

import com.andrej.springboot.model.dao.AddressDAO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone_number")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(name = "age")
    private byte age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressDAO address;
}
