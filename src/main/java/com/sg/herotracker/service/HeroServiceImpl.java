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
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.OrganizationHero;
import com.sg.herotracker.dto.Sighting;
import com.sg.herotracker.dto.SuperPower;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bdogg
 */
public class HeroServiceImpl implements HeroService {

    AddressDao addressDao;
    LocationDao locationDao;
    SuperPowerDao powerDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightingDao;
    HeroSightingDao heroSightingDao;
    OrganizationHeroDao orgHeroDao;

    public HeroServiceImpl(
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
    public Hero createNewHero(Hero h) {
        return heroDao.addHero(h);
    }

    @Override
    public Hero getHeroById(int heroId) {

        Hero hero = heroDao.getHeroById(heroId);

        if (hero != null) {
            hero = buildCompleteHero(hero);
        }

        return hero;
    }

    @Override
    public void updateHeroDetails(Hero h) {
        heroDao.updateHero(h);
    }

    @Override
    public void deleteHero(int heroId) {
        // this is going to throw an exception. should it?

        heroDao.deleteHero(heroId);
    }

    @Override
    public List<Hero> getHeroesByLocation(int locationId) {

        // get all the sightings for this location
        List<Sighting> sightings = sightingDao.getSightingsByLocation(locationId);

        // add all the associated HeroSightings relationships to one list.
        List<HeroSighting> hsList = new ArrayList<>();
        for (Sighting s : sightings) {
            List<HeroSighting> currentHS
                    = heroSightingDao.getHeroSightingsBySighting(
                            s.getSightingId()
                    );
            hsList.addAll(currentHS);
        }

        // filter down to only the unique hero IDs.
        Set<Integer> heroIdSet = new HashSet<>();
        for (HeroSighting currHS : hsList) {
            heroIdSet.add(currHS.getHero().getHeroId());
        }

        // push the Set into a List and return it
        List<Hero> heroList = new ArrayList<>();

        for (int heroId : heroIdSet) {
            // using service's getHeroById to ensure all details are retrieved.
            heroList.add(getHeroById(heroId));
        }

        return heroList;

    }

    @Override
    public List<Hero> getHeroesByOrganization(int orgId) {

        List<OrganizationHero> ohList = orgHeroDao.getMembershipsByOrg(orgId);

        List<Hero> heroesForOrg = new ArrayList<>();
        for (OrganizationHero oh : ohList) {
            heroesForOrg.add(oh.getHero());
        }

        return heroesForOrg;
    }
    
    // TODO test this. also result limits should be on the dao not service.
    @Override
    public List<Hero> getAllHeroes(int resultLimit) {
        List<Hero> allHeroes = heroDao.getAllHeroes();
        Collections.sort(allHeroes, Comparator.comparing(Hero::getName));
        List<Hero> heroes;
        if (allHeroes.size() >= resultLimit) {
            heroes = allHeroes.subList(0, resultLimit);
        } else {
            heroes = allHeroes.subList(0, allHeroes.size());
        }
        
        for (Hero h : heroes) {
            buildCompleteHero(h);
        }
        
        return heroes;
    }

    @Override
    public SuperPower createNewSuperPower(SuperPower p) {
        return powerDao.addSuperPower(p);
    }

    @Override
    public SuperPower getSuperPowerById(int powerId) {
        return powerDao.getSuperPowerById(powerId);
    }

    @Override
    public void updateSuperPowerDetails(SuperPower p) {
        powerDao.updateSuperPower(p);
    }

    @Override
    public void deleteSuperPower(int powerId) {
        powerDao.deleteSuperPower(powerId);
    }

    // Test
    @Override
    public List<SuperPower> getAllSuperPowers() {
        return powerDao.getAllSuperPowers();
    }
    
    private Hero buildCompleteHero(Hero h) {
        SuperPower power
                = powerDao.getSuperPowerById(h.getPower().getSuperPowerId());
        h.setSuperPower(power);
        return h;
    }

}
