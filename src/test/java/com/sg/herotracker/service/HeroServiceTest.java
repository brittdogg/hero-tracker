/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.service;

import com.sg.herotracker.dao.HeroDao;
import com.sg.herotracker.dao.SuperPowerDao;
import com.sg.herotracker.dao.TestData;
import com.sg.herotracker.dto.Hero;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author bdogg
 */
public class HeroServiceTest {

    HeroService heroService;
    HeroDao heroDao;
    SuperPowerDao powerDao;
    JdbcTemplate jdbcTemplate;

    // use these variables for things that would come from the user/view.
    SuperPower newPower;
    SuperPower powerFromDao;
    Hero hero1;

    public HeroServiceTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");

        heroService = ctx.getBean("heroService", HeroService.class);
        heroDao = ctx.getBean("heroDao", HeroDao.class);
        powerDao = ctx.getBean("superPowerDao", SuperPowerDao.class);
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
        newPower = new SuperPower();
        newPower.setName("My power name");
        newPower.setDescription("More information about my power");

        powerFromDao = powerDao.getSuperPowerById(3);

        hero1 = new Hero();
        hero1.setName("HeroName");
        hero1.setDescription("Good guy!");
        hero1.setSuperPower(powerFromDao);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateAndGetNewHero() {
        // service should take in hero assembled from user details,
        // then send it to the dao to be saved and given an id.
        // it should be assigned the correct superpower that was selected.
        // user-input hero details including selected power from existing data
        // are added to hero1 in test setup.
        hero1 = heroService.createNewHero(hero1);
        Hero fromDao = heroService.getHeroById(hero1.getHeroId());

        // assert that the hero has the expected heroId & superPowerId
        assertEquals(hero1.getHeroId(), fromDao.getHeroId());
        assertEquals(hero1.getPower().getSuperPowerId(),
                fromDao.getPower().getSuperPowerId());

    }

    @Test
    public void testGetHeroById() {
        // When it comes out of the service layer,
        // the hero should have its completed SuperPower object.

        // i.e., the hero id will be as expected,
        // the newPower id will be as expected,
        // and the required fields of the newPower will be there, not just id.
        Hero fromService = heroService.getHeroById(1);
        assertEquals(1, fromService.getHeroId());
        assertEquals(1, fromService.getPower().getSuperPowerId());
        assertNotNull(fromService.getPower().getName());
    }

    @Test
    public void testUpdateHeroDetails_ChangeNameAndPower() {
        /*
        The hero should always have the same id it had when you started.
        The detail that was changed should have, in fact, changed.
         */

        // pull a hero out of the database
        Hero heroToUpdate = heroService.getHeroById(3);
        
        // set the hero details
        heroToUpdate.setSuperPower(powerFromDao);
        heroToUpdate.setName("New Superhero Name");
        
        // now do the update
        heroService.updateHeroDetails(heroToUpdate);
        
        // get the hero out of the database again
        Hero fromDao = heroService.getHeroById(3);

        // verify the id is the same and the powers have been updated
        assertEquals(3, heroToUpdate.getHeroId());
        assertEquals(powerFromDao.getSuperPowerId(),
                fromDao.getPower().getSuperPowerId());
        assertEquals("New Superhero Name", fromDao.getName());

    }

    @Test
    public void testDeleteHero_NoSightings() {
        /*
        Hero should be deleted, PROVIDED that it has no dependencies,
        i.e. sightings.
        
        If Hero has dependencies, it will correctly throw
        DataIntegrityViolationException (I believe) for foreign key constraint.
         */
        try {
            heroService.deleteHero(5);
        } catch (Exception e) {
            fail("Did not delete hero");
        }
    }

    @Test
    public void testDeleteHero_WithSightings() {
        /*
        Hero should not be deleted when there are no dependencies.
        // TODO isn't this a database thing though? do I have to test this?
         */

        try {
            heroService.deleteHero(1);
            fail("Improperly deleted hero where sightings already exist");
        } catch (DataIntegrityViolationException ex) {
            return;
        }
    }

    @Test
    public void testGetHeroesByLocation() {
        /*
        Given a number of heroes sighting at a location,
        return each of those heroes. Test on list size match from test data.
        
        This method should not return duplicate heroes
        even if the hero has been seen at a location more than once.
         */

        List<Hero> heroesByLoc
                = heroService.getHeroesByLocation(1);
        assertEquals(3, heroesByLoc.size());

        // How can I check if I get the right superheroes? Should I?
//        Hero fromDao = heroService.getHeroById(1);
//        assertTrue(heroesByLoc.contains(fromDao));
    }

    @Test
    public void testGetHeroesByOrganization() {
        // validate that the expected number of heroes for an organization
        // is returned.

        // Stub DAOs need to contain the Heroes and their OrgHero relationships
        // to match the number in the assert statement.
        List<Hero> heroesByOrg
                = heroService.getHeroesByOrganization(1);
        assertEquals(2, heroesByOrg.size());
    }

    @Test
    public void testCreateAndGetNewSuperPower() {
        /*
        This is just CRUD pass-through.
        Make sure the add call returns the new SuperPower with an ID.
         */
        newPower = heroService.createNewSuperPower(newPower);
        SuperPower fromDao = heroService.getSuperPowerById(
                newPower.getSuperPowerId());

        // check the ID and details.
        assertEquals(newPower.getSuperPowerId(), fromDao.getSuperPowerId());
        assertEquals("My power name", fromDao.getName());
        assertEquals("More information about my power",
                fromDao.getDescription());
    }

    @Test
    public void testGetSuperPowerById_IdDoesNotExist() {
        SuperPower p = heroService.getSuperPowerById(8);
        assertNull(p);
    }

    @Test
    public void testUpdateSuperPowerDetails() {
        powerFromDao.setDescription("Newly updated description");
        heroService.updateSuperPowerDetails(powerFromDao);

    }

    @Test
    public void testDeleteSuperPower_NoHero() {
        try {
            heroService.deleteSuperPower(4);
        } catch (Exception e) {
            fail("Failed to delete an orphan superpower");
        }
    }

    @Test
    public void testDeleteSuperPower_WithHero() {
        // TODO do I really need to test this? It's a database thing.
        // unless I want to handle or translate the exception listed.
        try {
            heroService.deleteSuperPower(1);
            fail("Improperly deleted a superpower already attached to a hero");
        } catch (DataIntegrityViolationException ex) {
            return;
        }
    }

}
