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
import com.sg.herotracker.dto.Organization;
import com.sg.herotracker.dto.OrganizationHero;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bdogg
 */
public class OrganizationServiceImpl implements OrganizationService {

    AddressDao addressDao;
    LocationDao locationDao;
    SuperPowerDao powerDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightingDao;
    HeroSightingDao heroSightingDao;
    OrganizationHeroDao orgHeroDao;

    public OrganizationServiceImpl(
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
    public List<Organization> getOrgsByHero(int heroId) {

        List<OrganizationHero> ohList = orgHeroDao.getMembershipsByHero(heroId);

        List<Organization> orgs = new ArrayList<>();
        for (OrganizationHero oh : ohList) {
            orgs.add(oh.getOrg());
        }

        return orgs;
    }
    
    @Override
    public Organization createNewOrganization(Organization o) {
        return orgDao.addOrganization(o);
    }

    @Override
    public Organization getOrganizationById(int orgId) {
        Organization o = orgDao.getOrganizationById(orgId);
        if (o != null) {
            o = buildCompleteOrganization(o);
        }
        return o;
    }

    @Override
    public void updateOrganizationDetails(Organization o) {
        orgDao.updateOrganization(o);
    }

    @Override
    public void deleteOrganization(int orgId) {
        if (orgHeroDao.getMembershipsByOrg(orgId).isEmpty()) {
            orgDao.deleteOrganization(orgId);
        } // otherwise do what?
    }

    @Override
    public OrganizationHero addHeroToOrganization(Organization o, Hero h) {
        OrganizationHero oh = new OrganizationHero(h, o);
        oh = orgHeroDao.addMembership(oh);
        return oh;
    }
    
    // TODO Tests...
    @Override
    public void removeHeroFromOrganization(int orgHeroId) {
        orgHeroDao.deleteMembership(orgHeroId);
    }
    
    //TODO Tests...
    @Override
    public List<OrganizationHero> getMembersOfOrganization(int orgId) {
        return orgHeroDao.getMembershipsByOrg(orgId);
    }
    
    //TODO Tests...
    @Override
    public List<OrganizationHero> getMembershipsForHero(int heroId) {
        return orgHeroDao.getMembershipsByHero(heroId);
    }
    
    @Override
    public List<Organization> getAllOrgs() {
        return orgDao.getAllOrganizations();
    }

    private Organization buildCompleteOrganization(Organization o) {
        Address a
                = addressDao.getAddressById(o.getAddress().getAddressId());
        o.setAddress(a);
        return o;
    }

}
