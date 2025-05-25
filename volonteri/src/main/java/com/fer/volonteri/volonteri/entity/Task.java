package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zadatak")
public class Task {
    @Id
    @Column(name = "id_zadatak")
    private long id;

    @Column(name = "naziv")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "opis")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_aktivnost")
    private Activity activity;

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
