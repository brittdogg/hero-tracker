///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.Address;
//import com.sg.herotracker.dto.Location;
//import com.sg.herotracker.dto.Sighting;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// *
// * @author bdogg
// */
//public class SightingDaoTest {
//
//    SightingDao dao;
//    LocationDao locationDao;
//    AddressDao addressDao;
//
//    Sighting sighting1, sighting2, sighting3;
//    Location location1, location2;
//    Address address;
//
//    public SightingDaoTest() {
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//
//        dao = ctx.getBean("sightingDao", SightingDao.class);
//        locationDao = ctx.getBean("locationDao", LocationDao.class);
//        addressDao = ctx.getBean("addressDao", AddressDao.class);
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        // need a new address for the locations
//        address = new Address();
//        address.setStreet1("Location Street");
//        address.setCity("Chi City");
//        address.setState("Illinois");
//        address.setZip("60602");
//        addressDao.addAddress(address);
//
//        // set up some sighting locations
//        location1 = new Location();
//        location1.setAddress(address);
//        location1.setName("Location Name");
//        location1.setDescription("It's a place");
//        location1.setLatitudeDegrees(43.21f);
//        location1.setLatitudeDirection("N");
//        location1.setLongitudeDegrees(100.00f);
//        location1.setLongitudeDirection("E");
//        locationDao.addLocation(location1);
//
//        location2 = new Location();
//        location2.setAddress(address);
//        location2.setName("Location Name");
//        location2.setDescription("It's another place actually");
//        location2.setLatitudeDegrees(123.45f);
//        location2.setLatitudeDirection("S");
//        location2.setLongitudeDegrees(55.55f);
//        location2.setLongitudeDirection("W");
//        locationDao.addLocation(location2);
//
//        // create sightings to start with
//        // sightings 1 & 2 share the same location
//        // sightings 1 & 3 share the same date
//        // sightings 2 & 3 share the same time of day
//        sighting1 = new Sighting();
//        sighting1.setLocation(location1);
//        sighting1.setDateOccurred(LocalDate.of(2001, 01, 01));
//        sighting1.setTimeOccurred(LocalDateTime.of(2001, 01, 01, 12, 30));
//        dao.addSighting(sighting1);
//
//        sighting2 = new Sighting();
//        sighting2.setLocation(location1);
//        sighting2.setDateOccurred(LocalDate.of(1988, 8, 19));
//        sighting2.setTimeOccurred(LocalDateTime.of(1988, 8, 19, 01, 30));
//        dao.addSighting(sighting2);
//
//        sighting3 = new Sighting();
//        sighting3.setLocation(location2);
//        sighting3.setDateOccurred(LocalDate.of(2001, 01, 01));
//        sighting3.setTimeOccurred(LocalDateTime.of(2001, 01, 01, 01, 30));
//        dao.addSighting(sighting3);
//    }
//
//    @After
//    public void tearDown() {
////        List<Sighting> sightings = dao.getAllSightings();
////        for (Sighting s : sightings) {
////            dao.deleteSighting(s.getSightingId());
////        }
////
////        List<Location> locations = locationDao.getAllLocations();
////        for (Location l : locations) {
////            locationDao.deleteLocation(l.getLocationId());
////        }
////
////        List<Address> ads = addressDao.getAllAddresses();
////        for (Address a : ads) {
////            addressDao.deleteAddress(a.getAddressId());
////        }
//        TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addGetSighting() {
//        Sighting newSighting = new Sighting();
//        newSighting.setLocation(location1);
//        newSighting.setDateOccurred(LocalDate.of(2001, 01, 01));
//        newSighting.setTimeOccurred(LocalDateTime.of(2001, 01, 01, 12, 30));
//        newSighting = dao.addSighting(newSighting);
//
//        Sighting fromDao = dao.getSightingById(newSighting.getSightingId());
//
//        assertEquals(newSighting.getSightingId(), fromDao.getSightingId());
//        assertEquals(4, dao.getAllSightings().size());
//
//    }
//
//    @Test
//    public void updateSighting() {
//        sighting1.setDateOccurred(LocalDate.of(2017, 3, 22));
//        dao.updateSighting(sighting1);
//
//        Sighting fromDao = dao.getSightingById(sighting1.getSightingId());
//        assertEquals(sighting1.getSightingId(), fromDao.getSightingId());
//        assertEquals(sighting1.getDateOccurred(), fromDao.getDateOccurred());
//    }
//
//    @Test
//    public void deleteSighting() {
//        assertNotNull(dao.getSightingById(sighting3.getSightingId()));
//        dao.deleteSighting(sighting3.getSightingId());
//        assertNull(dao.getSightingById(sighting3.getSightingId()));
//        assertEquals(2, dao.getAllSightings().size());
//    }
//
//    @Test
//    public void getAllSightings() {
//        assertEquals(3, dao.getAllSightings().size());
//    }
//
//    @Test
//    public void getSightingsByLocation() {
//        int locationId1 = location1.getLocationId();
//        int locationId2 = location2.getLocationId();
//        assertEquals(2, dao.getSightingsByLocation(locationId1).size());
//        assertEquals(1, dao.getSightingsByLocation(locationId2).size());
//    }
//
//    @Test
//    public void getSightingsByDate() {
//        LocalDate sightingDate1 = sighting1.getDateOccurred();
//        LocalDate sightingDate2 = sighting2.getDateOccurred();
//        assertEquals(2, dao.getSightingsByDate(sightingDate1).size());
//        assertEquals(1, dao.getSightingsByDate(sightingDate2).size());
//    }
//
//}
