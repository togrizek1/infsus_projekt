package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "aktivnost")
public class Activity {

    @Id
    @Column(name = "id_aktivnost")
    private Long id;

    @Column(name = "naziv")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private ActivityStatus status;

    @Column(name = "datum")
    private Date date;

    @Column(name = "vrijeme")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "id_organizacija")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "id_statistika")
    private Statistics stats;

    @OneToOne
    @JoinColumn(name = "id_lokacija")
    private Location location;

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

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
