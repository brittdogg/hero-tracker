/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface SightingService {

    List<Sighting> getRecentSightings(int displayCount);

    List<HeroSighting> getRecentHeroSightings(int displayCount);

    List<Sighting> getSightingsByDate(LocalDate date);

    List<HeroSighting> getHeroSightingsByDate(LocalDate date);

    Sighting createNewSighting(Sighting s);

    Sighting getSightingById(int sightingId);

    void updateSightingDetails(Sighting s, List<Hero> heroes);

    void deleteSighting(int sightingId);

    //TODO need to test the methods that I've added to the interface.
    public void linkHeroToSighting(Hero hero, Sighting sighting);

    public void removeHeroFromSighting(int hsId);

    public List<HeroSighting> getHeroSightingRelationshipsBySighting(
            int sightingId);

    public List<HeroSighting> getHeroSightingRelationshipsByHero(int heroId);

}
