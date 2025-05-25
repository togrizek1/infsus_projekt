package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "korisnik")
public abstract class User {
    @Id
    @Column(name = "id_korisnik")
    private long id;

    @Column(name = "ime")
    private String name;

    @Column(name = "prezime")
    private String surname;

    private String email;

    @Column(name = "korisničko_ime")
    private String username;

    @Column(name = "lozinka")
    private String password;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
