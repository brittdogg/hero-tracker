/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dao.AddressDao;
import com.sg.herotracker.dao.TestData;
import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Location;
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
public class LocationServiceTest {

    LocationService locationService;
    AddressDao addressDao;
    JdbcTemplate jdbcTemplate;

    // use these variables for things that would come from the user/view.
    Address newAddress;
    Address existingAddress;
    Location newLocation;
    Location existingLocation;

    public LocationServiceTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");

        locationService = ctx.getBean("locationService", LocationService.class);
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

        TestData.buildTestSchema(jdbcTemplate);

        // Set up some happy path user data.
        newAddress = new Address();
        newAddress.setStreet1("123 Main Street");
        newAddress.setStreet2("Suite 300");
        newAddress.setCity("Deltona");
        newAddress.setState("FL");
        newAddress.setZip("30638");

        existingAddress = addressDao.getAddressById(1);

        newLocation = new Location();
        newLocation.setName("Location Name");
        newLocation.setDescription("This is where the first thing is");
        newLocation.setAddress(existingAddress);
        newLocation.setLatitudeDegrees(77.77f);
        newLocation.setLatitudeDirection("N");
        newLocation.setLongitudeDegrees(88.88f);
        newLocation.setLongitudeDirection("W");

        existingLocation = locationService.getLocationById(3);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetLocationsByHero() {
        /*
        Given a number of sightings made of a hero,
        return each sighting newLocation. Test on list size match from test data.
        
        This method should not return duplicate locations
        even if the hero has been seen at more than one newLocation.
         */

        List<Location> locationsByHero
                = locationService.getLocationsByHero(1);
        assertEquals(1, locationsByHero.size());

        // How can I check if I get the right superheroes? Should I?
    }

    @Test
    public void testCreateGetLocation() {
        newLocation = locationService.createLocation(newLocation);
        Location fromDao = locationService.getLocationById(
                newLocation.getLocationId());

        assertEquals(newLocation.getLocationId(), fromDao.getLocationId());
        assertEquals(newLocation.getName(), fromDao.getName());
        assertEquals(newLocation.getDescription(), fromDao.getDescription());
        assertEquals(newLocation.getAddress().getAddressId(),
                fromDao.getAddress().getAddressId());
        assertEquals(newLocation.getAddress().getCity(),
                fromDao.getAddress().getCity());
    }

    @Test
    public void testUpdateLocationDetails() {
        existingLocation.setAddress(existingAddress);
        existingLocation.setName("New Name");
        existingLocation.setLatitudeDegrees(43.34f);
        locationService.updateLocationDetails(existingLocation);
        Location fromDao = locationService.getLocationById(
                existingLocation.getLocationId());

        assertEquals(existingAddress.getAddressId(),
                fromDao.getAddress().getAddressId());
        assertEquals(existingAddress.getZip(), fromDao.getAddress().getZip());
        assertEquals("New Name", fromDao.getName());
        assertEquals(43.34f, fromDao.getLatitudeDegrees(), 0.001f);
    }

    @Test
    public void testDeleteLocation_HappyPath() {
        locationService.deleteLocation(3);
        assertNull(locationService.getLocationById(3));
    }

    @Test
    public void deleteLocation_HasSightings() {
        locationService.deleteLocation(2);
        assertNotNull(locationService.getLocationById(2));
    }

    @Test
    public void testCreateGetAddress() {
        newAddress = locationService.createAddress(newAddress);
        Address fromDao = locationService.getAddressById(
                newAddress.getAddressId());

        assertEquals(newAddress.getAddressId(), fromDao.getAddressId());
        assertEquals(newAddress.getStreet1(), fromDao.getStreet1());
        assertEquals(newAddress.getStreet2(), fromDao.getStreet2());
        assertEquals(newAddress.getCity(), fromDao.getCity());
        assertEquals(newAddress.getState(), fromDao.getState());
        assertEquals(newAddress.getZip(), fromDao.getZip());
    }

    @Test
    public void testUpdateAddressDetails() {

        existingAddress.setStreet1("Some New Street");
        existingAddress.setStreet2("Some new suite number");

        locationService.updateAddressDetails(existingAddress);

        Address fromDao = locationService.getAddressById(
                existingAddress.getAddressId());

        assertEquals(existingAddress.getAddressId(), fromDao.getAddressId());
        assertEquals("Some New Street", fromDao.getStreet1());
        assertEquals("Some new suite number", fromDao.getStreet2());

    }

    @Test
    public void deleteAddress_HappyPath() {
        locationService.deleteAddress(5);
        assertNull(locationService.getAddressById(5));
    }

    @Test
    public void deleteAddress_HasLocations() {
        locationService.deleteAddress(4);
        assertNotNull(locationService.getAddressById(4));
    }

    @Test
    public void deleteAddress_HasOrganizations() {
        locationService.deleteAddress(3);
        assertNotNull(locationService.getAddressById(3));
    }

}
