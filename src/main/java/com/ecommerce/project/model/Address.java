package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    @Size(max = 5, message = "Street must be at least 5 characters")
    private String street;

    @NotBlank
    @Size(max = 5, message = "building name must be at least 5 characters")
    private String buildingName;

    @NotBlank
    @Size(max = 3, message = "city must be at least 3 characters")
    private String city;

    @NotBlank
    @Size(max = 3, message = "state must be at least 3 characters")
    private String State;

    @NotBlank
    @Size(max = 3, message = "country must be at least 3 characters")
    private String country;

    @NotBlank
    @Size(max = 6, message = "pincode must be at least 6 characters")
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String pincode, String country, String state, String city, String buildingName, String street) {
        this.pincode = this.pincode;
        this.country = country;
        State = state;
        this.city = city;
        this.buildingName = buildingName;
        this.street = street;
    }
}
