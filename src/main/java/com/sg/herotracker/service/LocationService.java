/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Location;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface LocationService {

    List<Location> getLocationsByHero(int heroId);

    Location createLocation(Location l);

    Location getLocationById(int locationId);

    void updateLocationDetails(Location l);

    void deleteLocation(int locationId);
    
    Address createAddress(Address a);
    
    Address getAddressById(int addressId);
    
    void updateAddressDetails(Address a);
    
    void deleteAddress(int addressId);
    
    List<Location> getAllLocations();

}
