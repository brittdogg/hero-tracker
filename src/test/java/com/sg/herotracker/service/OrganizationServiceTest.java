/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dao.AddressDao;
import com.sg.herotracker.dao.HeroDao;
import com.sg.herotracker.dao.TestData;
import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.dto.Organization;
import com.sg.herotracker.dto.OrganizationHero;
import com.sg.herotracker.dto.Sighting;
import com.sg.herotracker.dto.SuperPower;
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
public class OrganizationServiceTest {

    OrganizationService orgService;
    HeroDao heroDao;
    AddressDao addressDao;
    JdbcTemplate jdbcTemplate;

    // use these variables for things that would come from the user/view.
    Address address;
    Address existingAddress;
    Location location;
    SuperPower power;
    Hero hero1;
    Hero hero2;
    Hero existingHero;
    List<Hero> heroes;
    Organization newOrg;
    Organization existingOrg;
    Organization emptyOrg;
    Sighting sighting;
    HeroSighting heroSighting;
    OrganizationHero orgHero;

    public OrganizationServiceTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");

        orgService = ctx.getBean("orgService", OrganizationService.class);
        heroDao = ctx.getBean("heroDao", HeroDao.class);
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

        existingAddress = addressDao.getAddressById(2);

        existingHero = heroDao.getHeroById(3);

        existingOrg = orgService.getOrganizationById(1);
        emptyOrg = orgService.getOrganizationById(4);

        newOrg = new Organization();
        newOrg.setName("Justice League");
        newOrg.setDescription("Good guys club");
        newOrg.setAddress(existingAddress);
        newOrg.setContactName("Mario");
        newOrg.setContactPhone("777-8686");
        newOrg.setContactEmail("mario@justiceleague.com");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetOrgsByHero() {
        List<Organization> orgs
                = orgService.getOrgsByHero(1);
        assertEquals(2, orgs.size());
    }

    @Test
    public void testCreateAndGetNewOrganization() {
        Organization o = orgService.createNewOrganization(newOrg);
        
        Organization fromDao = orgService.getOrganizationById(
                o.getOrgId());
        
        assertEquals(o.getOrgId(), fromDao.getOrgId());
        assertEquals(o.getName(), fromDao.getName());
        assertEquals(o.getAddress().getAddressId(),
                fromDao.getAddress().getAddressId());
    }

    @Test
    public void testUpdateOrganizationDetails() {
        // get an existing organization - done in setup
        
        // change the organization
        existingOrg.setAddress(existingAddress);
        existingOrg.setName("New Organization Name");
        
        // update it using orgService
        orgService.updateOrganizationDetails(existingOrg);
        
        // get it back out again
        Organization updatedOrg = orgService.getOrganizationById(1);
        
        // validate that the id is the same and update was made
        assertEquals(existingOrg.getOrgId(), updatedOrg.getOrgId());
        assertEquals(existingOrg.getAddress().getAddressId(),
                updatedOrg.getAddress().getAddressId());
        assertEquals(existingOrg.getAddress().getCity(),
                updatedOrg.getAddress().getCity());
    }

    @Test
    public void testDeleteOrganization_WithMembers() {
        // Should fail to delete.
        
        int orgId = existingOrg.getOrgId();
        orgService.deleteOrganization(orgId);
        assertNotNull(orgService.getOrganizationById(orgId));
    }
    
    @Test
    public void testDeleteOrganization_NoMembers() {
        // Should delete successfully.
        
        int orgId = emptyOrg.getOrgId();
        orgService.deleteOrganization(orgId);
        assertNull(orgService.getOrganizationById(orgId));
    }
    
    @Test
    public void addHeroToOrganization() {

        int heroId = existingHero.getHeroId();

        orgService.addHeroToOrganization(existingOrg, existingHero);
        
        // check that Organizations 1 & 3 are returned for Hero 3
        assertEquals(2, orgService.getOrgsByHero(heroId).size());
        
        // TODO how can I check that the correct ones are returned? should I?
    }

}
