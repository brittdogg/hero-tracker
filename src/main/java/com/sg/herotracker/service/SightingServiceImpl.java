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
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.dto.Sighting;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author bdogg
 */
public class SightingServiceImpl implements SightingService {

    AddressDao addressDao;
    LocationDao locationDao;
    SuperPowerDao powerDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightingDao;
    HeroSightingDao heroSightingDao;
    OrganizationHeroDao orgHeroDao;

    public SightingServiceImpl(
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
    public List<Sighting> getRecentSightings(int displayCount) {

        List<Sighting> allSightings = sightingDao.getAllSightings();
        List<Sighting> recentSightings;

        if (allSightings.size() < displayCount) {
            recentSightings = allSightings.subList(0, allSightings.size());
        } else {
            recentSightings = allSightings.subList(0, displayCount);
        }

        for (Sighting s : recentSightings) {
            buildCompleteSighting(s);
        }

        Collections.sort(recentSightings, Comparator.comparing(Sighting::getDateOccurred));

        return recentSightings;

    }

    // TODO ugh test this. also add and test sorting. may need custom comparator.
    @Override
    public List<HeroSighting> getRecentHeroSightings(int displayCount) {
        List<HeroSighting> allSightings = heroSightingDao.getAllHeroSightings();
        List<HeroSighting> recentSightings;

        if (allSightings.size() < displayCount) {
            recentSightings = allSightings.subList(0, allSightings.size());
        } else {
            recentSightings = allSightings.subList(0, displayCount);
        }

        return recentSightings;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        // get all the sighting records for this date.
        List<Sighting> sightings = sightingDao.getSightingsByDate(date);

        // for each sighting, find its Location record and attach it.
        for (Sighting s : sightings) {
            s = buildCompleteSighting(s);
        }

        return sightings;
    }

    @Override
    public List<HeroSighting> getHeroSightingsByDate(LocalDate date) {
        List<Sighting> sightings = sightingDao.getSightingsByDate(date);
        List<HeroSighting> heroSightings = new ArrayList<>();

        for (Sighting s : sightings) {
            List<HeroSighting> hs
                    = getHeroSightingRelationshipsBySighting(s.getSightingId());
            heroSightings.addAll(hs);
        }

        return heroSightings;
    }

    @Override
    public Sighting createNewSighting(Sighting s) {

        // construct Sighting object and add it to Sighting DAO
        s = sightingDao.addSighting(s);

        // add HeroSighting relationship(s)
//        for (Hero h : heroes) {
//            linkHeroToSighting(h, s);
//        }

        // return the new Sighting object, with its ID, to the caller
        return s;
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        Sighting s = sightingDao.getSightingById(sightingId);
        if (s == null) {
            return null;
        } else {
            s = buildCompleteSighting(s);
            return s;
        }
    }

    @Override
    public void updateSightingDetails(Sighting s, List<Hero> heroes) {

        // remove all pre-existing HeroSighting relationship(s)
        List<HeroSighting> previousHeroes
                = getHeroSightingRelationshipsBySighting(s.getSightingId());

        for (HeroSighting prevHs : previousHeroes) {
            removeHeroFromSighting(prevHs.getHeroSightingId());
        }

        // add the current ones
        for (Hero h : heroes) {
            linkHeroToSighting(h, s);
        }

        // update Sighting fields based on user form input
//        s.setLocation(locationDao.getLocationById(locationId));
//        s.setDateOccurred(date);
//        s.setTimeOccurred(time);
        sightingDao.updateSighting(s);
    }

    @Override
    public void deleteSighting(int sightingId) {
        // delete any associated HeroSightings first
        List<HeroSighting> hsList
                = getHeroSightingRelationshipsBySighting(sightingId);
        for (HeroSighting hs : hsList) {
            removeHeroFromSighting(hs.getHeroSightingId());
        }

        // then delete Sighting
        sightingDao.deleteSighting(sightingId);
    }

    /*
    Helper methods for bridge tables and composite objects
     */
    private Sighting buildCompleteSighting(Sighting s) {
        Location loc
                = locationDao.getLocationById(s.getLocation().getLocationId());
        s.setLocation(loc);

        Address ad
                = addressDao.getAddressById(s.getLocation().getLocationId());
        s.getLocation().setAddress(ad);

        return s;
    }

    @Override
    public void linkHeroToSighting(Hero hero, Sighting sighting) {
        HeroSighting hs = new HeroSighting(hero, sighting);
        heroSightingDao.addHeroSighting(hs);
    }

    @Override
    public void removeHeroFromSighting(int hsId) {
        heroSightingDao.deleteHeroSighting(hsId);
    }

    // TODO need to add tests for this now.
    @Override
    public List<HeroSighting> getHeroSightingRelationshipsBySighting(
            int sightingId) {

        // Methods on HeroSighting will build
        // the COMPLETE Hero and the COMPLETE Sighting
        List<HeroSighting> hsList
                = heroSightingDao.getHeroSightingsBySighting(sightingId);

        return hsList;
    }

    // TODO also need to add tests for this now.
    @Override
    public List<HeroSighting> getHeroSightingRelationshipsByHero(int heroId) {
        List<HeroSighting> hsList
                = heroSightingDao.getHeroSightingsByHero(heroId);

        return hsList;
    }

}
