package com.fer.volonteri.volonteri.dto;

public class ActivitySummaryDto {
    private long id;
    private String activityName;
    private String organizationName;

    public ActivitySummaryDto(long id, String activityName, String organizationName) {
        this.id = id;
        this.activityName = activityName;
        this.organizationName = organizationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
