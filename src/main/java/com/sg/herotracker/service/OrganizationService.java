/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.Organization;
import com.sg.herotracker.dto.OrganizationHero;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface OrganizationService {
    
    List<Organization> getOrgsByHero(int heroId);

    Organization createNewOrganization(Organization o);

    Organization getOrganizationById(int orgId);

    void updateOrganizationDetails(Organization o);

    void deleteOrganization(int orgId);
    
    List<Organization> getAllOrgs();
    
    OrganizationHero addHeroToOrganization(Organization o, Hero h);
    
    void removeHeroFromOrganization(int orgHeroId);
    
    List<OrganizationHero> getMembersOfOrganization(int orgId);
    
    public List<OrganizationHero> getMembershipsForHero(int heroId);
    
}
