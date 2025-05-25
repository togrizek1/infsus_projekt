package com.fer.volonteri.volonteri.entity;

import java.io.Serializable;
import java.util.Objects;

public class ExecutionId implements Serializable {
    private long volunteer;
    private long task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutionId that = (ExecutionId) o;
        return volunteer == that.volunteer && task == that.task;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteer, task);
    }
}
