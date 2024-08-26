package com.patika.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", length = 30)
    private String country;

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "district", length = 30)
    private String district;

    @Column(name = "neighborhood", length = 30)
    private String neighborhood;

    @Column(name = "street", length = 30)
    private String street;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "details", length = 200)
    private String details;

    @OneToOne(mappedBy = "address")
    private User user;

}
