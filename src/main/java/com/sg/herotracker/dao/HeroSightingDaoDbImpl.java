/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author bdogg
 */
public class HeroSightingDaoDbImpl implements HeroSightingDao {

    private static final String SQL_INSERT_HERO_SIGHTING
            = "INSERT INTO HeroSighting (HeroID, SightingID) "
            + "VALUES (?, ?)";

    private static final String SQL_UPDATE_HERO_SIGHTING
            = "UPDATE HeroSighting SET HeroID = ?, SightingID = ? "
            + "WHERE HeroSightingID = ?";

    private static final String SQL_DELETE_HERO_SIGHTING
            = "DELETE FROM HeroSighting WHERE HeroSightingID = ?";

    private static final String SQL_SELECT_ALL_HERO_SIGHTINGS
            = "SELECT hs.HeroSightingID, "
            + "h.HeroID, h.HeroName, h.Description, h.SuperPowerID, "
            + "sp.PowerName, sp.Description, "
            + "s.SightingID, s.SightingDate, s.SightingTime, s.LocationID, "
            + "l.LocationName, l.Description, l.LatitudeDegrees, l.LatitudeDirection, "
            + "l.LongitudeDegrees, l.LongitudeDirection, l.AddressID, "
            + "a.Street1, a.Street2, a.City, a.State, a.Zip "
            + "FROM HeroSighting hs "
            + "INNER JOIN Hero h ON h.HeroID = hs.HeroID "
            + "INNER JOIN SuperPower sp ON sp.SuperPowerID = h.SuperPowerID "
            + "INNER JOIN Sighting s ON s.SightingID = hs.SightingID "
            + "INNER JOIN Location l ON l.LocationID = s.LocationID "
            + "INNER JOIN Address a ON a.AddressID = l.AddressID";

    private static final String SQL_SELECT_HERO_SIGHTING
            = SQL_SELECT_ALL_HERO_SIGHTINGS + " WHERE hs.HeroSightingID = ?";

    private static final String SQL_FIND_EXISTING_HERO_SIGHTING_RELATIONSHIP
            = SQL_SELECT_ALL_HERO_SIGHTINGS
            + " WHERE hs.HeroID = ? AND hs.SightingID = ?";

    private static final String SQL_SELECT_RELATIONSHIPS_BY_HERO
            = SQL_SELECT_ALL_HERO_SIGHTINGS
            + " WHERE hs.HeroID = ?";

    private static final String SQL_SELECT_RELATIONSHIPS_BY_SIGHTING
            = SQL_SELECT_ALL_HERO_SIGHTINGS
            + " WHERE hs.SightingID = ?";

    private final JdbcTemplate jdbcTemplate;

    public HeroSightingDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public HeroSighting addHeroSighting(HeroSighting heroSighting) {
        jdbcTemplate.update(SQL_INSERT_HERO_SIGHTING,
                heroSighting.getHero().getHeroId(),
                heroSighting.getSighting().getSightingId()
        );

        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        heroSighting.setHeroSightingId(id);

        return heroSighting;
    }

    @Override
    public void updateHeroSighting(HeroSighting heroSighting) {
        jdbcTemplate.update(SQL_UPDATE_HERO_SIGHTING,
                heroSighting.getHero().getHeroId(),
                heroSighting.getSighting().getSightingId(),
                heroSighting.getHeroSightingId()
        );
    }

    @Override
    public void deleteHeroSighting(int heroSightingId) {
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING, heroSightingId);
    }

    @Override
    public HeroSighting getHeroSightingByRelationshipId(int heroSightingId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_HERO_SIGHTING,
                    new HeroSightingMapper(), heroSightingId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<HeroSighting> getAllHeroSightings() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_HERO_SIGHTINGS, new HeroSightingMapper());
    }

    @Override
    public HeroSighting getHeroSightingByHeroAndSightingIds(
            int heroId, int sightingId) {
        // this uniqueness should be enforced by the database
        // but currently it probably isn't
        try {
            return jdbcTemplate.queryForObject(
                    SQL_FIND_EXISTING_HERO_SIGHTING_RELATIONSHIP,
                    new HeroSightingMapper(),
                    heroId, sightingId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<HeroSighting> getHeroSightingsByHero(int heroId) {
        return jdbcTemplate.query(
                SQL_SELECT_RELATIONSHIPS_BY_HERO,
                new HeroSightingMapper(), heroId
        );
    }

    @Override
    public List<HeroSighting> getHeroSightingsBySighting(int sightingId) {
        return jdbcTemplate.query(
                SQL_SELECT_RELATIONSHIPS_BY_SIGHTING,
                new HeroSightingMapper(), sightingId
        );
    }

    private static final class HeroSightingMapper implements RowMapper<HeroSighting> {

        @Override
        public HeroSighting mapRow(ResultSet rs, int i) throws SQLException {
            HeroSighting heroSighting = new HeroSighting();

            heroSighting.setHeroSightingId(rs.getInt("hs.HeroSightingID"));

            // Build hero and sub-object SuperPower
            Hero h = new Hero();
            h.setHeroId(rs.getInt("h.HeroID"));
            h.setName(rs.getString("h.HeroName"));
            h.setDescription(rs.getString("h.Description"));
            SuperPower p = new SuperPower();
            p.setSuperPowerId(rs.getInt("h.SuperPowerID"));
            p.setName(rs.getString("sp.PowerName"));
            p.setDescription(rs.getString("sp.Description"));
            h.setSuperPower(p);

            // Build Sighting and sub-objects Location >> Address
            Sighting s = new Sighting();
            s.setSightingId(rs.getInt("s.SightingID"));
            s.setDateOccurred(rs.getDate("s.SightingDate").toLocalDate());
            Timestamp time = rs.getTimestamp("s.SightingTime");
            if (!rs.wasNull()) {
                s.setTimeOccurred(rs.getTimestamp("s.SightingTime").toLocalDateTime());
            }

            Location l = new Location();
            l.setLocationId(rs.getInt("s.LocationID"));
            l.setName(rs.getString("l.LocationName"));
            l.setDescription(rs.getString("l.Description"));
            l.setLatitudeDegrees(rs.getFloat("l.LatitudeDegrees"));
            l.setLatitudeDirection(rs.getString("l.LatitudeDirection"));
            l.setLongitudeDegrees(rs.getFloat("l.LongitudeDegrees"));
            l.setLongitudeDirection(rs.getString("l.LongitudeDirection"));

            Address a = new Address();
            a.setAddressId(rs.getInt("l.AddressID"));
            a.setStreet1(rs.getString("a.Street1"));
            a.setStreet2(rs.getString("a.Street2"));
            a.setCity(rs.getString("a.City"));
            a.setState(rs.getString("a.State"));
            a.setZip(rs.getString("a.Zip"));

            l.setAddress(a);

            s.setLocation(l);

            heroSighting.setHero(h);
            heroSighting.setSighting(s);

            return heroSighting;
        }

    }

}
