/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dto;

/**
 *
 * @author bdogg
 */
public class OrganizationHero {
    
    private int orgHeroId;
    private Hero hero;
    private Organization org;
    
    public OrganizationHero() {
        
    }
    
    public OrganizationHero(Hero hero, Organization org) {
        this.hero = hero;
        this.org = org;
    }

    public int getOrgHeroId() {
        return orgHeroId;
    }

    public void setOrgHeroId(int orgHeroId) {
        this.orgHeroId = orgHeroId;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
    
}
