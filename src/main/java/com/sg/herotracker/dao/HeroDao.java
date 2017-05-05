/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.Hero;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface HeroDao {
    
    Hero addHero(Hero hero);
    
    void updateHero(Hero hero);
    
    void deleteHero(int heroId);
    
    Hero getHeroById(int heroId);
    
    List<Hero> getHeroesByName(String heroName);
    
    List<Hero> getHeroesByOrganization(int orgId);
    
    List<Hero> getHeroesByLocationSighted(int locationId);
    
    List<Hero> getAllHeroes();
    
}
