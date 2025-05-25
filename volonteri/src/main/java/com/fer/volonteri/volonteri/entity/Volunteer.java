package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "volonter")
public class Volunteer extends User{
    @ManyToOne
    @JoinColumn(name = "id_statistika")
    private Statistics stats;

    @ManyToOne
    @JoinColumn(name = "id_zadatak")
    private Task task;

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
