package com.fer.volonteri.volonteri.entity;

import java.io.Serializable;
import java.util.Objects;

public class ParticipationId implements Serializable {

    private long user;
    private long activity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationId that = (ParticipationId) o;
        return user == that.user && activity == that.activity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, activity);
    }
}
