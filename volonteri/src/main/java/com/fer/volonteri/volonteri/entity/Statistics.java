package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistika")
public class Statistics {

    @Id
    @Column(name = "id_statistika")
    private long id;

    @Column(name = "broj_odradenih_zadataka")
    private int numOfDoneTasks;

    @Column(name = "broj_odradenih_sati")
    private float numOfHours;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumOfDoneTasks() {
        return numOfDoneTasks;
    }

    public void setNumOfDoneTasks(int numOfDoneTasks) {
        this.numOfDoneTasks = numOfDoneTasks;
    }

    public float getNumOfHours() {
        return numOfHours;
    }

    public void setNumOfHours(float numOfHours) {
        this.numOfHours = numOfHours;
    }
}
