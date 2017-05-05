///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.SuperPower;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import static org.junit.Assert.*;
//
///**
// *
// * @author bdogg
// */
//public class SuperPowerDaoTest {
//
//    SuperPowerDao dao;
//
//    public SuperPowerDaoTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//
//        dao = ctx.getBean("superPowerDao", SuperPowerDao.class);
//    }
//
//    @After
//    public void tearDown() {
////        List<SuperPower> powers = dao.getAllSuperPowers();
////        for (SuperPower p : powers) {
////            dao.deleteSuperPower(p.getSuperPowerId());
////        }
//        TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addAndGetSuperPower() {
//        SuperPower power = new SuperPower();
//        power.setName("Salad Fingers");
//        power.setDescription("Creepy af");
//
//        dao.addSuperPower(power);
//
//        SuperPower fromDao = dao.getSuperPowerById(power.getSuperPowerId());
//
//        assertEquals(power.getSuperPowerId(), fromDao.getSuperPowerId());
//    }
//
//    @Test
//    public void updateSuperPower() {
//        SuperPower power = new SuperPower();
//        power.setName("Salad Fingers");
//        power.setDescription("Creepy af");
//
//        dao.addSuperPower(power);
//
//        power.setDescription("Even creepier than originally predicted");
//
//        dao.updateSuperPower(power);
//        SuperPower fromDao = dao.getSuperPowerById(power.getSuperPowerId());
//
//        assertEquals(power.getSuperPowerId(), fromDao.getSuperPowerId());
//        assertEquals(power.getDescription(), fromDao.getDescription());
//    }
//
//    @Test
//    public void testDeleteSuperPower() {
//        SuperPower power = new SuperPower();
//        power.setName("Salad Fingers");
//        power.setDescription("Creepy af");
//
//        dao.addSuperPower(power);
//
//        dao.deleteSuperPower(power.getSuperPowerId());
//
//        assertNull(dao.getSuperPowerById(power.getSuperPowerId()));
//    }
//
//}
