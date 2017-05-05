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
//public class HeroDaoTest {
//
//    // data access
//    HeroDao dao;
//    SuperPowerDao powerDao;
//    AddressDao addressDao;
//    OrganizationDao orgDao;
//    OrganizationHeroDao orgHeroDao;
//    
//    // test data variables to be filled in setup
//    SuperPower power;
//    Address address;
//    Hero hero1, hero2, hero3;
//    Organization org1, org2;
//    OrganizationHero orgHero1, orgHero2, orgHero3, orgHero4;
//
//    public HeroDaoTest() {
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//        dao = ctx.getBean("heroDao", HeroDao.class);
//        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
//        addressDao = ctx.getBean("addressDao", AddressDao.class);
//        orgDao = ctx.getBean("orgDao", OrganizationDao.class);
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
//        // Super Power to assign to all test heroes.
//
//        power = new SuperPower();
//        power.setName("My power name");
//        power.setDescription("More information about my power");
//        powerDao.addSuperPower(power);
//        
//        // Stock Heroes to get, update, and search for.
//
//        hero1 = new Hero();
//        hero1.setName("HeroName");
//        hero1.setDescription("Good guy!");
//        hero1.setSuperPower(power);
//        hero1 = dao.addHero(hero1);
//
//        hero2 = new Hero();
//        hero2.setName("HeroName");
//        hero2.setDescription("Bad guy!");
//        hero2.setSuperPower(power);
//        hero2 = dao.addHero(hero2);
//
//        hero3 = new Hero();
//        hero3.setName("Something Else");
//        hero3.setDescription("Double Agent!");
//        hero3.setSuperPower(power);
//        hero3 = dao.addHero(hero3);
//        
//        // Address to assign to test organizations
//        
//        address = new Address();
//        address.setStreet1("Street1");
//        address.setStreet2("Street2");
//        address.setCity("Boston");
//        address.setState("MA");
//        address.setZip("12345");
//        
//        // Organizations for heroes to belong to.
//        
//        org1 = new Organization();
//        org1.setName("Justice League");
//        org1.setDescription("Good guys");
//        org1.setAddress(address);
//        org1.setContactName("Nancy");
//        org1.setContactPhone("1231456");
//        org1.setContactEmail("nancy@justiceleague.com");
//        
//        org2 = new Organization();
//        org2.setName("League of Doom");
//        org2.setDescription("Bad guys");
//        org2.setAddress(address);
//        org2.setContactName("Frederick");
//        org2.setContactPhone("666-6666");
//        org2.setContactEmail("MrChance@doomleague.com");
//        
//        // Hero-Organization relationships
//        
//        orgHero1 = new OrganizationHero(hero1, org1);
//        orgHero2 = new OrganizationHero(hero2, org2);
//        orgHero3 = new OrganizationHero(hero3, org1);
//        orgHero4 = new OrganizationHero(hero3, org2);
//    }
//
//    @After
//    public void tearDown() {
//        
//        // Remove heroes and their super powers
////        List<Hero> heroes = dao.getAllHeroes();
////        for (Hero h : heroes) {
////            dao.deleteHero(h.getHeroId());
////        }
////        powerDao.deleteSuperPower(power.getSuperPowerId());
////        
////        // Remove organization-hero relationships
////        List<OrganizationHero> members = orgHeroDao.getAllMemberships();
////        for (OrganizationHero oh : members) {
////            orgHeroDao.deleteMembership(oh.getOrgHeroId());
////        }
////        
////        // Remove organizations and their address
////        List<Organization> orgs = orgDao.getAllOrganizations();
////        for (Organization o : orgs) {
////            orgDao.deleteOrganization(o.getOrgId());
////        }
////        addressDao.deleteAddress(address.getAddressId());
//
//TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addAndGetHero() {
//        Hero hero = new Hero();
//        hero.setName("My New Hero");
//        hero.setDescription("Description goes here");
//        hero.setSuperPower(power);
//
//        hero = dao.addHero(hero);
//
//        Hero fromDao = dao.getHeroById(hero.getHeroId());
//
//        assertEquals(hero.getHeroId(), fromDao.getHeroId());
//    }
//
//    @Test
//    public void updateHero() {
//        
//        // change the Description for Hero 1
//        hero1.setDescription("Some new, far more badass description");
//        dao.updateHero(hero1);
//
//        Hero fromDao = dao.getHeroById(hero1.getHeroId());
//
//        assertEquals(hero1.getHeroId(), fromDao.getHeroId());
//        assertEquals(hero1.getDescription(), fromDao.getDescription());
//    }
//
//    @Test
//    public void deleteHero() {
//
//        assertNotNull(dao.getHeroById(hero1.getHeroId()));
//
//        dao.deleteHero(hero1.getHeroId());
//
//        assertNull(dao.getHeroById(hero1.getHeroId()));
//
//    }
//
//    @Test
//    public void getHeroesByName() {
//
//        List<Hero> heroes = dao.getHeroesByName("HeroName");
//
//        assertEquals(2, heroes.size());
//    }
//
//    @Test
//    public void testGetHeroesByOrganization() {
//        // select * from Hero h 
//        // join OrganizationHero oh on h.HeroID = oh.HeroID
//        // where oh.OrganizationID = ?;
////        fail("Test not implemented");
//        List<Hero> heroesForOrg =
//                dao.getHeroesByOrganization(orgHero1.getOrg().getOrgId());
//    }
//}
