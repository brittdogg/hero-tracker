/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dao.HeroDao;
import com.sg.herotracker.dao.HeroSightingDao;
import com.sg.herotracker.dao.LocationDao;
import com.sg.herotracker.dao.TestData;
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.dto.Sighting;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class SightingServiceTest {

    SightingService sightingService;
    HeroDao heroDao;
    HeroSightingDao heroSightingDao;
    LocationDao locationDao;
    JdbcTemplate jdbcTemplate;

    // use these variables for things that would come from the user/view.
    Location locationFromDao;
    List<Hero> heroesFromDao;
    Sighting newSighting;
    Sighting sightingFromDao;

    public SightingServiceTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");

        sightingService = ctx.getBean("sightingService", SightingService.class);
        heroDao = ctx.getBean("heroDao", HeroDao.class);
        heroSightingDao = ctx.getBean("heroSightingDao", HeroSightingDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);
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

        TestData.buildTestSchema(jdbcTemplate);
        
        // Set up some happy path user data.

        heroesFromDao = new ArrayList<>();
        heroesFromDao.add(heroDao.getHeroById(1));
        heroesFromDao.add(heroDao.getHeroById(2));

        locationFromDao = locationDao.getLocationById(1);
        
        newSighting = new Sighting();
        newSighting.setLocation(locationFromDao);
        newSighting.setDateOccurred(LocalDate.of(2001, 01, 01));
        newSighting.setTimeOccurred(LocalDateTime.of(2001, 01, 01, 12, 30));
        
        sightingFromDao = sightingService.getSightingById(3);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetRecentSightings_LimitLessThanTotal() {

        List<Sighting> results = sightingService.getRecentSightings(2);
        assertEquals(2, results.size());
    }

    @Test
    public void testGetRecentSightings_LimitGreaterThanTotal() {
        List<Sighting> results = sightingService.getRecentSightings(10);
        assertEquals(3, results.size());
    }

    @Test
    public void testGetSightingsByDate_DateHasSightings() {
        List<Sighting> results
                = sightingService.getSightingsByDate(LocalDate.of(2017, 3, 22));
        assertEquals(2, results.size());
    }
    
    @Test
    public void testGetHeroSightingsByDate_DateHasSightings() {
        List<HeroSighting> results
                = sightingService.getHeroSightingsByDate(
                        LocalDate.of(2017, 3, 22));
        assertEquals(4, results.size());
        
        // TODO should probably check the integrity of all the objects
        // underneath the Hero and Sighting here?
        // maybe more of a DAO thing.
        
    }

    @Test
    public void testCreateNewSighting_RequiredFieldsOnly() {
        /*
        When a newSighting report is created, it needs to have:
        - SightingID + required fields
        - an associated Location (which itself has an address)
        - at least one associated hero (i.e. entries in HeroSightings table)
        */
        Sighting testSighting
                = sightingService.createNewSighting(newSighting);

        assertNotNull(testSighting.getSightingId());
        assertEquals(LocalDate.of(2001, 1, 1), testSighting.getDateOccurred());
        assertEquals(LocalDateTime.of(2001, 01, 01, 12, 30), testSighting.getTimeOccurred());
        assertNotNull(testSighting.getLocation().getLocationId());
        assertNotNull(testSighting.getLocation().getAddress().getAddressId());
        
//        List<HeroSighting> sightingsAdded = heroSightingDao.
//                getHeroSightingsBySighting(testSighting.getSightingId());
//        
//        assertEquals(2, sightingsAdded.size());
        
    }

    @Test
    public void testGetSightingById_ValidId() {
        Sighting testSighting
                = sightingService.getSightingById(1);

        // check object ID and a property, then sub-object ID and a property
        assertEquals(1, testSighting.getSightingId());
        assertEquals(LocalDate.of(2017, 3, 22), testSighting.getDateOccurred());
        assertEquals(1, testSighting.getLocation().getLocationId());
        assertEquals("Something Snappy", testSighting.getLocation().getName());
        
    }

    @Test
    public void testUpdateSightingDetails_ChangeLocationAndDate() {

        // Sighting from DAO is defined in setup
        
        // change properties of the sighting
        sightingFromDao.setLocation(locationDao.getLocationById(2));
        sightingFromDao.setDateOccurred(LocalDate.of(2017, 2, 2));
        
        // update it
        sightingService.updateSightingDetails(sightingFromDao, heroesFromDao);

        // validate ID and details for Sighting object
        assertEquals(3, sightingFromDao.getSightingId());
        assertEquals(LocalDate.of(2017, 2, 2), sightingFromDao.getDateOccurred());
        assertNull(sightingFromDao.getTimeOccurred());

        // validate ID and details for Location sub-object
        assertEquals(2, sightingFromDao.getLocation().getLocationId());
        assertEquals("Name #2", sightingFromDao.getLocation().getName());

        // validate ID (and details?????) for Address sub-object
        assertEquals(2, sightingFromDao.getLocation().getAddress().getAddressId());
//        assertEquals("987 Road", sightingFromDao.getLocation().getAddress().getStreet1());
    }

    @Test
    public void testDeleteSighting_ExistingId() {
        sightingService.deleteSighting(1);
        assertNull(sightingService.getSightingById(1));
    }

}
