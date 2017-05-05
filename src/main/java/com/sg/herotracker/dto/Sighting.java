/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author bdogg
 */
public class Sighting {
    
    private int sightingId;
    private Location location;
    private LocalDate dateOccurred;
    private LocalDateTime timeOccurred;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDateOccurred() {
        return dateOccurred;
    }

    public void setDateOccurred(LocalDate dateOccurred) {
        this.dateOccurred = dateOccurred;
    }

    public LocalDateTime getTimeOccurred() {
        return timeOccurred;
    }

    public void setTimeOccurred(LocalDateTime timeOccurred) {
        this.timeOccurred = timeOccurred;
    }
    
}
