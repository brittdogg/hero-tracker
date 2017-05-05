/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bdogg
 */
public class HeroDaoDbImpl implements HeroDao {

    private static final String SQL_INSERT_HERO
            = "INSERT INTO Hero (HeroName, Description, SuperPowerID) "
            + "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE_HERO
            = "UPDATE Hero SET HeroName = ?, Description = ?, SuperPowerID = ? "
            + "WHERE HeroID = ?";

    private static final String SQL_DELETE_HERO
            = "DELETE FROM Hero WHERE HeroID = ?";

    private static final String SQL_SELECT_HERO
            = "SELECT * FROM Hero WHERE HeroID = ?";
    
    // TODO Name search should be refactored to use PreparedStatement
    // to use the LIKE operator
    private static final String SQL_SELECT_HEROES_BY_NAME
            = "SELECT * FROM Hero WHERE HeroName = ?";

    private static final String SQL_SELECT_HEROES_BY_ORGANIZATION
            = "SELECT * FROM Hero h "
            + "JOIN OrganizationHero oh ON oh.HeroID = h.HeroID "
            + "WHERE oh.OrgID = ?";

    private static final String SQL_SELECT_ALL_HEROES
            = "SELECT * FROM Hero";

    private final JdbcTemplate jdbcTemplate;

    public HeroDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero addHero(Hero hero) {
        jdbcTemplate.update(SQL_INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getPower().getSuperPowerId());
        // service is going to do the relationships?

        int heroId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);

        hero.setHeroId(heroId);

        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        jdbcTemplate.update(SQL_UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getPower().getSuperPowerId(),
                hero.getHeroId());
    }

    @Override
    public void deleteHero(int heroId) {
        // all the things associated with hero (HeroOrganization, HeroSighting
        // must also be deleted before this will work.
        jdbcTemplate.update(SQL_DELETE_HERO, heroId);
    }

    @Override
    public Hero getHeroById(int heroId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_HERO, new HeroMapper(), heroId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getHeroesByName(String heroName) {
        return jdbcTemplate.query(
                SQL_SELECT_HEROES_BY_NAME, new HeroMapper(), heroName);
    }

    @Override
    public List<Hero> getHeroesByOrganization(int orgId) {
        return jdbcTemplate.query(
                SQL_SELECT_HEROES_BY_ORGANIZATION, new HeroMapper(), orgId);
    }

    @Override
    public List<Hero> getAllHeroes() {
        return jdbcTemplate.query(SQL_SELECT_ALL_HEROES, new HeroMapper());
    }

    @Override
    public List<Hero> getHeroesByLocationSighted(int locationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            Hero hero = new Hero();

            hero.setHeroId(rs.getInt("HeroID"));
            hero.setName(rs.getString("HeroName"));
            
            // set a superpower with just an ID
            SuperPower power = new SuperPower();
            power.setSuperPowerId(rs.getInt("SuperPowerID"));
            hero.setSuperPower(power);
            
            hero.setDescription(rs.getString("Description"));

            return hero;
        }

    }

}
