/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dao.AddressDao;
import com.sg.herotracker.dao.HeroDao;
import com.sg.herotracker.dao.HeroSightingDao;
import com.sg.herotracker.dao.LocationDao;
import com.sg.herotracker.dao.OrganizationDao;
import com.sg.herotracker.dao.OrganizationHeroDao;
import com.sg.herotracker.dao.SightingDao;
import com.sg.herotracker.dao.SuperPowerDao;
import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Location;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author bdogg
 */
public class LocationServiceImpl implements LocationService {

    AddressDao addressDao;
    LocationDao locationDao;
    SuperPowerDao powerDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightingDao;
    HeroSightingDao heroSightingDao;
    OrganizationHeroDao orgHeroDao;

    public LocationServiceImpl(
            AddressDao addressDao,
            LocationDao locationDao,
            SuperPowerDao powerDao,
            HeroDao heroDao,
            OrganizationDao orgDao,
            SightingDao sightingDao,
            HeroSightingDao heroSightingDao,
            OrganizationHeroDao orgHeroDao) {
        this.addressDao = addressDao;
        this.locationDao = locationDao;
        this.powerDao = powerDao;
        this.heroDao = heroDao;
        this.orgDao = orgDao;
        this.sightingDao = sightingDao;
        this.heroSightingDao = heroSightingDao;
        this.orgHeroDao = orgHeroDao;
    }

    @Override
    public List<Location> getLocationsByHero(int heroId) {

        // initialize a final output list for locations
        List<Location> locations = new ArrayList<>();

        // get all the HeroSightings (i.e. Hero/Location joins)
        List<HeroSighting> hsList
                = heroSightingDao.getHeroSightingsByHero(heroId);

        // for each hero-sighting pull the location ID into a set (avoids dupes)
        Set<Integer> locIds = new HashSet<>();
        for (HeroSighting hs : hsList) {
            int locId = hs.getSighting().getLocation().getLocationId();
            locIds.add(locId);
        }

        // get the full location for each id.
        // TODO this is repetitive. I had the whole Location before.
        // Maybe I should've just kept the bridge rowmappers basic.
        for (int locId : locIds) {
            locations.add(getLocationById(locId));
        }

        return locations;
    }

    @Override
    public Location createLocation(Location l) {
        return locationDao.addLocation(l);
    }

    @Override
    public Location getLocationById(int locationId) {
        Location l = locationDao.getLocationById(locationId);
        if (l != null) {
            l = buildCompleteLocation(l);
        }
        return l;
    }

    @Override
    public void updateLocationDetails(Location l) {
        locationDao.updateLocation(l);
    }

    @Override
    public void deleteLocation(int locationId) {
        if (sightingDao.getSightingsByLocation(locationId).isEmpty()) {
            locationDao.deleteLocation(locationId);
        } // otherwise what?
    }

    @Override
    public Address createAddress(Address a) {
        return addressDao.addAddress(a);
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressDao.getAddressById(addressId);
    }

    @Override
    public void updateAddressDetails(Address a) {
        addressDao.updateAddress(a);
    }

    @Override
    public void deleteAddress(int addressId) {
        // currently have no way of searching other objects by Address
        // so I'm just using the exception directly instead.
        try {
            addressDao.deleteAddress(addressId);
        } catch (DataIntegrityViolationException ex) {
            return;
        }
    }
    
    @Override
    public List<Location> getAllLocations() {
        List<Location> allLocs = locationDao.getAllLocations();
        for (Location l : allLocs) {
            buildCompleteLocation(l);
        }
        
        return allLocs;
    }

    private Location buildCompleteLocation(Location l) {
        Address a
                = addressDao.getAddressById(l.getAddress().getAddressId());
        l.setAddress(a);
        return l;
    }

}
