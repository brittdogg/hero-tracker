/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author bdogg
 */
public class LocationDaoDbImpl implements LocationDao {

    private static final String SQL_INSERT_LOCATION
            = "INSERT INTO Location (AddressID, LocationName, Description, "
            + "LatitudeDegrees, LatitudeDirection, "
            + "LongitudeDegrees, LongitudeDirection) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_LOCATION
            = "UPDATE Location SET "
            + "AddressID = ?, LocationName = ?, Description = ?, "
            + "LatitudeDegrees = ?, LatitudeDirection = ?, "
            + "LongitudeDegrees = ?, LongitudeDirection = ? "
            + "WHERE LocationID = ?";

    private static final String SQL_DELETE_LOCATION
            = "DELETE FROM Location WHERE LocationID = ?";

    private static final String SQL_SELECT_LOCATION
            = "SELECT * FROM Location WHERE LocationID = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "SELECT * FROM Location";

    private static final String SQL_SELECT_LOCATIONS_FOR_HERO
            = "SELECT * FROM Location l "
            + "JOIN Sighting s ON l.LocationID = s.LocationID "
            + "JOIN HeroSighting hs ON hs.SightingID = s.SightingID "
            + "WHERE hs.HeroID = ?";

    private final JdbcTemplate jdbcTemplate;

    public LocationDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getAddress().getAddressId(),
                location.getName(),
                location.getDescription(),
                location.getLatitudeDegrees(),
                location.getLatitudeDirection(),
                location.getLongitudeDegrees(),
                location.getLongitudeDirection()
        );

        int locId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);

        location.setLocationId(locId);

        return location;
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getAddress().getAddressId(),
                location.getName(),
                location.getDescription(),
                location.getLatitudeDegrees(),
                location.getLatitudeDirection(),
                location.getLongitudeDegrees(),
                location.getLongitudeDirection(),
                location.getLocationId()
        );
    }

    @Override
    public void deleteLocation(int locationId) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_LOCATION, new LocationMapper(), locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocationsForHero(int heroId) {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();

            location.setLocationId(rs.getInt("LocationID"));
            location.setName(rs.getString("LocationName"));

            // get just the Address ID.
            Address a = new Address();
            a.setAddressId(rs.getInt("AddressID"));
            location.setAddress(a);

            String locDesc = rs.getString("Description");
            if (!rs.wasNull()) {
                location.setDescription(locDesc);
            }
            location.setLatitudeDegrees(rs.getFloat("LatitudeDegrees"));
            location.setLatitudeDirection(rs.getString("LatitudeDirection"));
            location.setLongitudeDegrees(rs.getFloat("LongitudeDegrees"));
            location.setLongitudeDirection(rs.getString("LongitudeDirection"));

            return location;
        }
    }

}
