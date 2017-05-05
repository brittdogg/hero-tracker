/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dto;

/**
 *
 * @author bdogg
 */
public class Location {
    
    private int locationId;
    private String name;
    private String description;
    private Address address;
    private float latitudeDegrees;
    private String latitudeDirection;
    private float longitudeDegrees;
    private String longitudeDirection;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public void setLatitudeDegrees(float latitudeDegrees) {
        this.latitudeDegrees = latitudeDegrees;
    }

    public String getLatitudeDirection() {
        return latitudeDirection;
    }

    public void setLatitudeDirection(String latitudeDirection) {
        this.latitudeDirection = latitudeDirection;
    }

    public float getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public void setLongitudeDegrees(float longitudeDegrees) {
        this.longitudeDegrees = longitudeDegrees;
    }

    public String getLongitudeDirection() {
        return longitudeDirection;
    }

    public void setLongitudeDirection(String longitudeDirection) {
        this.longitudeDirection = longitudeDirection;
    }
    
}
