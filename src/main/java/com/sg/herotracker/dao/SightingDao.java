/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface SightingDao {
    
    Sighting addSighting(Sighting sighting);
    
    void updateSighting(Sighting sighting);
    
    void deleteSighting(int sightingId);
    
    Sighting getSightingById(int sightingId);
    
    List<Sighting> getAllSightings();
    
    List<Sighting> getSightingsByLocation(int locationId);
    
    List<Sighting> getSightingsByDate(LocalDate date);
    
}
