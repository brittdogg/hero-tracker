///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.Address;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// *
// * @author bdogg
// */
//public class AddressDaoTest {
//
//    AddressDao dao;
//    JdbcTemplate jdbcTemplate;
//
//    public AddressDaoTest() {
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//
//        dao = ctx.getBean("addressDao", AddressDao.class);
//        jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
//
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//
//    }
//
//    @Before
//    public void setUp() {
//
//    }
//
//    @After
//    public void tearDown() {
//        //TODO I could delete the data if I implement getAllAddresses
//        // however, is it good practice to have methods for testing only?
////        List<Address> addresses = dao.getAllAddresses();
////
////        for (Address a : addresses) {
////            dao.deleteAddress(a.getAddressId());
////        }
//
//        TestData.destroyTestSchema(jdbcTemplate);
//    }
//
//    @Test
//    public void addAndGetAddress() {
//
//        // test address includes required fields, no ID
//        Address address = new Address();
//        address.setStreet1("123 Main Street");
////        address.setStreet2("any value");
//        address.setCity("Deltona");
//        address.setState("FL");
//        address.setZip("60638");
//
//        // CREATE the address
//        dao.addAddress(address);
//
//        // READ it back out of the database
//        Address fromDao = dao.getAddressById(address.getAddressId());
//
//        // TODO test for equality? or some other way? just checking ID for now.
//        assertEquals(address.getAddressId(), fromDao.getAddressId());
//    }
//
//    @Test
//    public void updateAddress() {
//
//        Address address = new Address();
//        address.setStreet1("123 Main Street");
//        address.setCity("Deltona");
//        address.setState("FL");
//        address.setZip("60638");
//
//        dao.addAddress(address);
//
//        // UPDATE address to add an optional field (street2)
//        address.setStreet2("Suite #507");
//        dao.updateAddress(address);
//        Address fromDao = dao.getAddressById(address.getAddressId());
//
//        assertEquals(address.getAddressId(), fromDao.getAddressId());
//        assertEquals(address.getStreet2(), fromDao.getStreet2());
//    }
//
//    @Test
//    public void deleteAddress() {
//        // test address includes required fields, no ID
//        Address address = new Address();
//        address.setStreet1("123 Main Street");
//        address.setCity("Deltona");
//        address.setState("FL");
//        address.setZip("60638");
//
//        dao.addAddress(address);
//
//        // DELETE address
//        int idToDelete = address.getAddressId();
//        dao.deleteAddress(idToDelete);
//        assertNull(dao.getAddressById(idToDelete));
//
//    }
//}
