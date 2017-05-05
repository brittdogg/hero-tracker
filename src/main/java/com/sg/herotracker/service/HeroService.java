/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.OrganizationHero;
import com.sg.herotracker.dto.SuperPower;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface HeroService {
    
    Hero createNewHero(Hero h);

    Hero getHeroById(int heroId);

    void updateHeroDetails(Hero h);

    void deleteHero(int heroId);

    List<Hero> getHeroesByLocation(int locationId);

    List<Hero> getHeroesByOrganization(int orgId);
    
    List<Hero> getAllHeroes(int resultLimit);

    SuperPower createNewSuperPower(SuperPower p);

    SuperPower getSuperPowerById(int powerId);

    void updateSuperPowerDetails(SuperPower p);

    void deleteSuperPower(int powerId);
    
    List<SuperPower> getAllSuperPowers();

}
