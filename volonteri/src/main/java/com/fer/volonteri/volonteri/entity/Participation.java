package com.fer.volonteri.volonteri.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sudjelovanje")
@IdClass(ParticipationId.class)
public class Participation {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_korisnik")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_aktivnost")
    private Activity activity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
