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
public class HeroSighting {
    
    private int heroSightingId;
    private Hero hero;
    private Sighting sighting;
    
    public HeroSighting(Hero h, Sighting s) {
        this.hero = h;
        this.sighting = s;
    }
    
    public HeroSighting() {
        
    }

    public int getHeroSightingId() {
        return heroSightingId;
    }

    public void setHeroSightingId(int heroSightingId) {
        this.heroSightingId = heroSightingId;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }
    
}
