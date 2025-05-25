package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "izvrsavanje")
@IdClass(ExecutionId.class)
public class Execution {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_korisnik")
    private Volunteer volunteer;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_zadatak")
    private Task task;

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
