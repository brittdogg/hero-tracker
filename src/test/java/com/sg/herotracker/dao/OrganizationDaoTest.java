///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.Address;
//import com.sg.herotracker.dto.Hero;
//import com.sg.herotracker.dto.Organization;
//import com.sg.herotracker.dto.OrganizationHero;
//import com.sg.herotracker.dto.SuperPower;
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
//public class OrganizationDaoTest {
//    
//    OrganizationDao dao;
//    AddressDao addressDao;
//    
//    SuperPowerDao powerDao;
//    HeroDao heroDao;
//    OrganizationHeroDao orgHeroDao;
//    
//    Organization org1, org2, org3;
//    Address address;
//    SuperPower power;
//    Hero hero1, hero2, hero3;
//    OrganizationHero membership1, membership2;
//    
//    public OrganizationDaoTest() {
//        ApplicationContext ctx = 
//                new ClassPathXmlApplicationContext("test-applicationContext.xml");
//        dao = ctx.getBean("orgDao", OrganizationDao.class);
//        addressDao = ctx.getBean("addressDao", AddressDao.class);
//        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
//        heroDao = ctx.getBean("heroDao", HeroDao.class);
//        orgHeroDao = ctx.getBean("orgHeroDao", OrganizationHeroDao.class);
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
//        
//        address = new Address();
//        address.setStreet1("123 Street");
//        address.setCity("Orlando");
//        address.setState("FL");
//        address.setZip("30368");
//        addressDao.addAddress(address);
//        
//        org1 = new Organization();
//        org1.setName("Justice League");
//        org1.setDescription("Good guys club");
//        org1.setAddress(address);
//        org1.setContactName("Mario");
//        org1.setContactPhone("777-8686");
//        org1.setContactEmail("mario@justiceleague.com");
//        org1 = dao.addOrganization(org1);
//        
//        org2 = new Organization();
//        org2.setName("League of Doom");
//        org2.setDescription("Bad guys club");
//        org2.setAddress(address);
//        org2.setContactName("Wario");
//        org2.setContactPhone("666-6868");
//        org2.setContactEmail("wario@doomleague.com");
//        org2 = dao.addOrganization(org2);
//     
//        org3 = new Organization();
//        org3.setName("Avengers");
//        org3.setDescription("Like Justice League but for Marvel");
//        org3.setAddress(address);
//        org3.setContactName("Nick");
//        org3.setContactPhone("123-9876");
//        org3.setContactEmail("nickfury@shield.com");
//        org3 = dao.addOrganization(org3);
//    }
//    
//    @After
//    public void tearDown() {
//
//        // remove Organizations
////        List<Organization> orgs = dao.getAllOrganizations();
////        for (Organization o : orgs) {
////            dao.deleteOrganization(o.getOrgId());
////        }
////        
////        // remove test Address
////        List<Address> ads = addressDao.getAllAddresses();
////        for (Address a : ads) {
////            addressDao.deleteAddress(a.getAddressId());
////        }
//
//TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addGetOrganization() {
//        Organization newOrg = new Organization();
//        newOrg.setName("A new organization");
//        newOrg.setDescription("something");
//        newOrg.setAddress(address);
//        newOrg.setContactName("Britt Dogg");
//        newOrg.setContactPhone("84848 ext 123");
//        newOrg.setContactEmail("bdogg@org.com");
//        
//        newOrg = dao.addOrganization(newOrg);
//        
//        Organization fromDao = dao.getOrganizationById(newOrg.getOrgId());
//        
//        assertEquals(newOrg.getOrgId(), fromDao.getOrgId());
//        assertEquals(4, dao.getAllOrganizations().size());
//    }
//
//    @Test
//    public void updateOrganization() {
//        org3.setName("S.H.I.E.L.D.");
//        dao.updateOrganization(org3);
//        
//        Organization fromDao = dao.getOrganizationById(org3.getOrgId());
//        
//        // check IDs and names
//        assertEquals(org3.getOrgId(), fromDao.getOrgId());
//        assertEquals("S.H.I.E.L.D.", fromDao.getName());
//    }
//
//    @Test
//    public void deleteOrganization() {
//        dao.deleteOrganization(org3.getOrgId());
//        
//        assertNull(dao.getOrganizationById(org3.getOrgId()));
//        assertEquals(2, dao.getAllOrganizations().size());
//    }
//
//    @Test
//    public void getAllOrganizations() {
//        List<Organization> orgs = dao.getAllOrganizations();
//        
//        assertEquals(3, orgs.size());
//    }
//    
//}
