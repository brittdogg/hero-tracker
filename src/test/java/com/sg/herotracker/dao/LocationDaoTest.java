///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.Address;
//import com.sg.herotracker.dto.Location;
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
//public class LocationDaoTest {
//
//    LocationDao dao;
//    AddressDao addressDao;
//
//    SuperPowerDao powerDao;
//    HeroDao heroDao;
//    SightingDao sightingDao;
//    HeroSightingDao hsDao;
//
//    Address address;
//    Location location1, location2, location3;
//
//    public LocationDaoTest() {
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//        dao = ctx.getBean("locationDao", LocationDao.class);
//        addressDao = ctx.getBean("addressDao", AddressDao.class);
//
//        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
//        heroDao = ctx.getBean("heroDao", HeroDao.class);
//        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
//        hsDao = ctx.getBean("heroSightingDao", HeroSightingDao.class);
//
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
//        // need Address to make a Location
//        address = new Address();
//        address.setStreet1("123 Main");
//        address.setCity("New York");
//        address.setState("New York");
//        address.setZip("10001");
//        addressDao.addAddress(address);
//
//        // make test locations
//        location1 = new Location();
//        location1.setName("Location Name");
//        location1.setDescription("This is where the first thing is");
//        location1.setAddress(address);
//        location1.setLatitudeDegrees(77.77f);
//        location1.setLatitudeDirection("W");
//        location1.setLongitudeDegrees(88.88f);
//        location1.setLongitudeDirection("N");
//        dao.addLocation(location1);
//
//        location2 = new Location();
//        location2.setName("Location Name");
//        location2.setDescription("This is where the second thing is");
//        location2.setAddress(address);
//        location2.setLatitudeDegrees(98.76f);
//        location2.setLatitudeDirection("E");
//        location2.setLongitudeDegrees(123.45f);
//        location2.setLongitudeDirection("N");
//        dao.addLocation(location2);
//
//        location3 = new Location();
//        location3.setName("Location Name 3");
//        location3.setDescription("This is where the third thing is");
//        location3.setAddress(address);
//        location3.setLatitudeDegrees(25.00f);
//        location3.setLatitudeDirection("E");
//        location3.setLongitudeDegrees(12.34f);
//        location3.setLongitudeDirection("S");
//        dao.addLocation(location3);
//
//    }
//
//    @After
//    public void tearDown() {
//        // remove test locations
////        List<Location> locs = dao.getAllLocations();
////        for (Location l : locs) {
////            dao.deleteLocation(l.getLocationId());
////        }
////
////        // remove test addresses
////        List<Address> ads = addressDao.getAllAddresses();
////        for (Address a : ads) {
////            addressDao.deleteAddress(a.getAddressId());
////        }
//TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addGetLocation() {
//
//        Location newLoc = new Location();
//        newLoc.setName("A fourth location name");
//        newLoc.setDescription("A place of great power");
//        newLoc.setAddress(address);
//        newLoc.setLatitudeDegrees(4.00f);
//        newLoc.setLatitudeDirection("E");
//        newLoc.setLongitudeDegrees(23.99f);
//        newLoc.setLongitudeDirection("N");
//        
//        newLoc = dao.addLocation(newLoc);
//        
//        Location fromDao = dao.getLocationById(newLoc.getLocationId());
//        
//        assertEquals(newLoc.getLocationId(), fromDao.getLocationId());
//        assertEquals(4, dao.getAllLocations().size());
//    }
//
//    @Test
//    public void updateLocation() {
//        location1.setDescription("This is a new description");
//        dao.updateLocation(location1);
//        
//        Location fromDao = dao.getLocationById(location1.getLocationId());
//        
//        assertEquals(location1.getLocationId(), fromDao.getLocationId());
//        assertEquals(location1.getDescription(), fromDao.getDescription());
//    }
//
//    @Test
//    public void deleteLocation() {
//        
//        assertNotNull(dao.getLocationById(location3.getLocationId()));
//        
//        dao.deleteLocation(location3.getLocationId());
//        
//        assertNull(dao.getLocationById(location3.getLocationId()));
//        
//    }
//
//    @Test
//    public void getAllLocations() {
//        assertEquals(3, dao.getAllLocations().size());
//    }
//
//}
