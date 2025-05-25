package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lokacija")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lokacija")
    private Long id;

    @Column(name = "grad")
    private String city;

    @Column(name = "država")
    private String country;

    @Column(name = "adresa")
    private String address;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
