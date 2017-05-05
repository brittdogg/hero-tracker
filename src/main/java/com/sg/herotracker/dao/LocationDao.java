/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface LocationDao {
    
    Location addLocation(Location location);
    
    void updateLocation(Location location);
    
    void deleteLocation(int locationId);
    
    Location getLocationById(int locationId);
    
    List<Location> getAllLocations();
    
    List<Location> getAllLocationsForHero(int heroId);
    
}
