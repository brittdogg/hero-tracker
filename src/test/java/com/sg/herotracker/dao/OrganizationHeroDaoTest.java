///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.herotracker.dao;
//
//import com.sg.herotracker.dto.Address;
//import com.sg.herotracker.dto.Hero;
//import com.sg.herotracker.dto.HeroSighting;
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
//public class OrganizationHeroDaoTest {
//
//    OrganizationHeroDao dao;
//    OrganizationDao orgDao;
//    HeroDao heroDao;
//    HeroSightingDao heroSightingDao;
//
//    AddressDao addressDao;
//    SuperPowerDao powerDao;
//
//    SuperPower power;
//    Address address;
//    Hero hero1, hero2, hero3;
//    Organization org1, org2;
//    OrganizationHero oh1, oh2, oh3;
//
//    public OrganizationHeroDaoTest() {
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext(
//                        "test-applicationContext.xml");
//
//        dao = ctx.getBean("orgHeroDao", OrganizationHeroDao.class);
//        orgDao = ctx.getBean("orgDao", OrganizationDao.class);
//        heroDao = ctx.getBean("heroDao", HeroDao.class);
//        addressDao = ctx.getBean("addressDao", AddressDao.class);
//        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
//        heroSightingDao = ctx.getBean("heroSightingDao", HeroSightingDao.class);
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
//        power = new SuperPower();
//        power.setName("Super strength & shit");
//        power.setDescription("Did I stutter");
//        power = powerDao.addSuperPower(power);
//
//        address = new Address();
//        address.setStreet1("777 New Street");
//        address.setCity("Omaha");
//        address.setState("NE");
//        address.setZip("69103");
//        address = addressDao.addAddress(address);
//
//        hero1 = new Hero();
//        hero1.setName("HeroName");
//        hero1.setDescription("Good guy!");
//        hero1.setSuperPower(power);
//        hero1 = heroDao.addHero(hero1);
//
//        hero2 = new Hero();
//        hero2.setName("HeroName");
//        hero2.setDescription("Bad guy!");
//        hero2.setSuperPower(power);
//        hero2 = heroDao.addHero(hero2);
//
//        hero3 = new Hero();
//        hero3.setName("Something Else");
//        hero3.setDescription("Double Agent!");
//        hero3.setSuperPower(power);
//        hero3 = heroDao.addHero(hero3);
//
//        org1 = new Organization();
//        org1.setName("Justice League");
//        org1.setDescription("Good guys club");
//        org1.setAddress(address);
//        org1.setContactName("Mario");
//        org1.setContactPhone("777-8686");
//        org1.setContactEmail("mario@justiceleague.com");
//        org1 = orgDao.addOrganization(org1);
//
//        org2 = new Organization();
//        org2.setName("League of Doom");
//        org2.setDescription("Bad guys club");
//        org2.setAddress(address);
//        org2.setContactName("Wario");
//        org2.setContactPhone("666-6868");
//        org2.setContactEmail("wario@doomleague.com");
//        org2 = orgDao.addOrganization(org2);
//
//        oh1 = new OrganizationHero(hero1, org1);
//        dao.addMembership(oh1);
//        oh2 = new OrganizationHero(hero2, org1);
//        dao.addMembership(oh2);
//        oh3 = new OrganizationHero(hero3, org2);
//        dao.addMembership(oh3);
//
//    }
//
//    @After
//    public void tearDown() {
////        List<OrganizationHero> ohList = dao.getAllMemberships();
////        for (OrganizationHero oh : ohList) {
////            dao.deleteMembership(oh.getOrgHeroId());
////        }
////
////        List<Organization> orgs = orgDao.getAllOrganizations();
////        for (Organization o : orgs) {
////            orgDao.deleteOrganization(o.getOrgId());
////        }
////        
////        List<HeroSighting> heroSightings = heroSightingDao.getAllHeroSightings();
////        for (HeroSighting hs : heroSightings) {
////            heroSightingDao.deleteHeroSighting(hs.getHeroSightingId());
////        }
////        
////        List<Hero> heroes = heroDao.getAllHeroes();
////        for (Hero h : heroes) {
////            heroDao.deleteHero(h.getHeroId());
////        }
////
////        List<Address> ads = addressDao.getAllAddresses();
////        for (Address a : ads) {
////            addressDao.deleteAddress(a.getAddressId());
////        }
////
////        List<SuperPower> powers = powerDao.getAllSuperPowers();
////        for (SuperPower p : powers) {
////            powerDao.deleteSuperPower(p.getSuperPowerId());
////        }
//TestData.destroyTestSchema();
//    }
//
//    @Test
//    public void addGetMembership() {
//        OrganizationHero newOh = new OrganizationHero(hero1, org2);
//        newOh = dao.addMembership(newOh);
//
//        OrganizationHero fromDao = dao.getMembership(newOh.getOrgHeroId());
//
//        assertEquals(newOh.getOrgHeroId(), fromDao.getOrgHeroId());
//        assertEquals(4, dao.getAllMemberships().size());
//    }
//
//    @Test
//    public void getMembershipByHeroAndOrgIds() {
//        OrganizationHero fromDao
//                = dao.getMembershipByHeroAndOrgIds(
//                        hero1.getHeroId(), org1.getOrgId());
//
//        assertEquals(oh1.getOrgHeroId(), fromDao.getOrgHeroId());
//    }
//
//    @Test
//    public void updateMembership_ChangeHero() {
//        oh1.setHero(hero2);
//        dao.updateMembership(oh1);
//
//        OrganizationHero fromDao
//                = dao.getMembership(oh1.getOrgHeroId());
//
//        // verify relationship ID remains the same and HeroID has changed.
//        assertEquals(oh1.getOrgHeroId(), fromDao.getOrgHeroId());
//        assertEquals(oh1.getHero().getHeroId(), fromDao.getHero().getHeroId());
//    }
//
//    @Test
//    public void updateMembership_ChangeOrg() {
//        oh1.setOrg(org2);
//        dao.updateMembership(oh1);
//
//        OrganizationHero fromDao
//                = dao.getMembership(oh1.getOrgHeroId());
//        
//        // verify relationship ID remains the same and OrgID has changed.
//        assertEquals(oh1.getOrgHeroId(), fromDao.getOrgHeroId());
//        assertEquals(oh1.getOrg().getOrgId(), fromDao.getOrg().getOrgId());
//    }
//
//    @Test
//    public void deleteMembership() {
//        assertNotNull(dao.getMembership(oh1.getOrgHeroId()));
//        dao.deleteMembership(oh1.getOrgHeroId());
//        assertNull(dao.getMembership(oh1.getOrgHeroId()));
//    }
//
//    @Test
//    public void getAllMemberships() {
//        assertEquals(3, dao.getAllMemberships().size());
//    }
//
//}
