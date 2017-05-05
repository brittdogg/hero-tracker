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
public interface OrganizationHeroDao {
    
    OrganizationHero addMembership(OrganizationHero orgHero);
    
    void updateMembership(OrganizationHero orgHero);
    
    void deleteMembership(int orgHeroId);
    
    OrganizationHero getMembership(int orgHeroId);
    
    OrganizationHero getMembershipByHeroAndOrgIds(int heroId, int orgId);
    
    List<OrganizationHero> getMembershipsByHero(int heroId);
    
    List<OrganizationHero> getMembershipsByOrg(int orgId);
    
    List<OrganizationHero> getAllMemberships();
    
}
