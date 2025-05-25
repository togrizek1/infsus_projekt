package com.fer.volonteri.volonteri.dto;

import com.fer.volonteri.volonteri.entity.ActivityStatus;
import com.fer.volonteri.volonteri.entity.Location;
import com.fer.volonteri.volonteri.entity.Volunteer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ActivityDetailDto {
    private Long id;
    private String name;
    private ActivityStatus status;
    private String date;
    private String time;
    private OrganizationDto organization;
    private Location location;
    private List<UserDto> volunteers;

    public ActivityDetailDto() { }

    public ActivityDetailDto(Long id, String name, ActivityStatus status, String date, String time, OrganizationDto organization,Location location, List<UserDto> volunteers) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.date = date;
        this.time = time;
        this.organization = organization;
        this.location = location;
        this.volunteers = volunteers;
    }

    public static class OrganizationDto {
        private Long id;
        private String name;
        private String email;

        public OrganizationDto() { }

        public OrganizationDto(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class LocationDto {
        private Long id;
        private String city;
        private String country;
        private String address;

        public LocationDto() { }

        public LocationDto(Long id, String city, String country, String address) {
            this.id = id;
            this.city = city;
            this.country = country;
            this.address = address;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class UserDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        public UserDto(Long id, String firstName, String lastName, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
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

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<UserDto> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<UserDto> volunteers) {
        this.volunteers = volunteers;
    }
}
