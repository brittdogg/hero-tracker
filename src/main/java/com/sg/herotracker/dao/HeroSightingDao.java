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
public interface HeroSightingDao {
    
    HeroSighting addHeroSighting(HeroSighting heroSighting);
    
    void updateHeroSighting(HeroSighting heroSighting);
    
    void deleteHeroSighting(int heroSightingId);
    
    HeroSighting getHeroSightingByRelationshipId(int heroSightingId);
    
    HeroSighting getHeroSightingByHeroAndSightingIds(int heroId, int sightingId);
    
    List<HeroSighting> getHeroSightingsByHero(int heroId);
    
    List<HeroSighting> getHeroSightingsBySighting(int sightingId);
    
    List<HeroSighting> getAllHeroSightings();
    
}
