package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aktivnost_status")
public class ActivityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long id;

    @Column(name = "naziv", nullable = false, unique = true)
    private String name;

    public ActivityStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActivityStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
