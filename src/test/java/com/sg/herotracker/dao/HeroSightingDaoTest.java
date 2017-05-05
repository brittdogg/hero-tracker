/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.dto.Sighting;
import com.sg.herotracker.dto.SuperPower;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author bdogg
 */
public class HeroSightingDaoTest {
    
    HeroSightingDao dao;
    HeroDao heroDao;
    SightingDao sightingDao;
    
    SuperPowerDao powerDao;
    LocationDao locationDao;
    AddressDao addressDao;
    
    HeroSighting hs1, hs2, hs3;
    Hero hero1, hero2, hero3;
    Sighting sighting1, sighting2, sighting3;
    Location location;
    Address address;
    SuperPower power;
    
    JdbcTemplate jdbcTemplate;
    
    public HeroSightingDaoTest() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");
        
        dao = ctx.getBean("heroSightingDao", HeroSightingDao.class);
        heroDao = ctx.getBean("heroDao", HeroDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);
        addressDao = ctx.getBean("addressDao", AddressDao.class);
        jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        TestData.destroyTestSchema(jdbcTemplate);
        
        address = new Address();
        address.setStreet1("Blah Street");
        address.setCity("Madison");
        address.setState("Wisconsin");
        address.setZip("55443");
        address = addressDao.addAddress(address);
        
        location = new Location();
        location.setName("Location Name");
        location.setDescription("This is where the first thing is");
        location.setAddress(address);
        location.setLatitudeDegrees(77.77f);
        location.setLatitudeDirection("W");
        location.setLongitudeDegrees(88.88f);
        location.setLongitudeDirection("N");
        location = locationDao.addLocation(location);
        
        power = new SuperPower();
        power.setName("Being Dumb");
        power.setDescription("Goes without saying");
        power = powerDao.addSuperPower(power);
        
        sighting1 = new Sighting();
        sighting1.setLocation(location);
        sighting1.setDateOccurred(LocalDate.of(2001, 01, 01));
        sighting1.setTimeOccurred(LocalDateTime.of(2001, 01, 01, 12, 30));
        sighting1 = sightingDao.addSighting(sighting1);
        
        sighting2 = new Sighting();
        sighting2.setLocation(location);
        sighting2.setDateOccurred(LocalDate.of(2002, 02, 02));
        sighting2.setTimeOccurred(LocalDateTime.of(2002, 02, 02, 12, 30));
        sighting2 = sightingDao.addSighting(sighting2);
        
        hero1 = new Hero();
        hero1.setName("Space Ghost");
        hero1.setDescription("Coast to Coast");
        hero1.setSuperPower(power);
        hero1 = heroDao.addHero(hero1);
        
        hero2 = new Hero();
        hero2.setName("Zorak");
        hero2.setDescription("Hissss");
        hero2.setSuperPower(power);
        hero2 = heroDao.addHero(hero2);
        
        hs1 = new HeroSighting();
        hs1.setHero(hero1);
        hs1.setSighting(sighting1);
        hs1 = dao.addHeroSighting(hs1);
        
        hs2 = new HeroSighting();
        hs2.setHero(hero1);
        hs2.setSighting(sighting2);
        hs2 = dao.addHeroSighting(hs2);
        
        hs3 = new HeroSighting();
        hs3.setHero(hero2);
        hs3.setSighting(sighting1);
        hs3 = dao.addHeroSighting(hs3);
    }
    
    @After
    public void tearDown() {
//        List<HeroSighting> hsList = dao.getAllHeroSightings();
//        for (HeroSighting hs : hsList) {
//            dao.deleteHeroSighting(hs.getHeroSightingId());
//        }
//        
//        List<Sighting> sightings = sightingDao.getAllSightings();
//        for (Sighting s : sightings) {
//            sightingDao.deleteSighting(s.getSightingId());
//        }
//        
//        List<Hero> heroes = heroDao.getAllHeroes();
//        for (Hero h : heroes) {
//            heroDao.deleteHero(h.getHeroId());
//        }
//        
//        List<SuperPower> powers = powerDao.getAllSuperPowers();
//        for (SuperPower p : powers) {
//            powerDao.deleteSuperPower(p.getSuperPowerId());
//        }
//        
//        List<Location> locations = locationDao.getAllLocations();
//        for (Location l : locations) {
//            locationDao.deleteLocation(l.getLocationId());
//        }
//        
//        List<Address> addresses = addressDao.getAllAddresses();
//        for (Address a : addresses) {
//            addressDao.deleteAddress(a.getAddressId());
//        }
                
    }

    @Test
    public void addGetHeroSightingById() {
        HeroSighting newHs = new HeroSighting();
        newHs.setHero(hero1);
        newHs.setSighting(sighting1);
        newHs = dao.addHeroSighting(newHs);
        
        HeroSighting fromDao =
                dao.getHeroSightingByRelationshipId(newHs.getHeroSightingId());
        
        assertEquals(newHs.getHeroSightingId(), fromDao.getHeroSightingId());
        assertEquals(4, dao.getAllHeroSightings().size());
        assertEquals(address.getAddressId(), fromDao.getSighting().getLocation().getAddress().getAddressId());
        assertEquals(address.getCity(), fromDao.getSighting().getLocation().getAddress().getCity());
    }

    @Test
    public void updateHeroSightingRelationship_ChangeHero() {
        hs1.setHero(hero2);
        dao.updateHeroSighting(hs1);
        
        HeroSighting fromDao =
                dao.getHeroSightingByRelationshipId(hs1.getHeroSightingId());
        
        // verify relationship ID remains the same and HeroID has changed.
        assertEquals(hs1.getHeroSightingId(), fromDao.getHeroSightingId());
        assertEquals(hs1.getHero().getHeroId(), fromDao.getHero().getHeroId());
    }
    
    @Test
    public void updateHeroSightingRelationship_ChangeSighting() {
        hs1.setSighting(sighting2);
        dao.updateHeroSighting(hs1);
        
        HeroSighting fromDao =
                dao.getHeroSightingByRelationshipId(hs1.getHeroSightingId());
        
        // verify relationship ID remains the same and SightingID has changed.
        assertEquals(hs1.getHeroSightingId(), fromDao.getHeroSightingId());
        assertEquals(hs1.getSighting().getSightingId(),
                fromDao.getSighting().getSightingId());
        
    }

    @Test
    public void deleteHeroSightingRelationship() {
        assertNotNull(hs1.getHeroSightingId());
        dao.deleteHeroSighting(hs1.getHeroSightingId());
        assertNull(dao.getHeroSightingByRelationshipId(hs1.getHeroSightingId()));
    }

    @Test
    public void getAllHeroSightings() {
        assertEquals(3, dao.getAllHeroSightings().size());
    }

}
