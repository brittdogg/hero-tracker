/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author bdogg
 */
public class SightingDaoDbImpl implements SightingDao {

    private static final String SQL_INSERT_SIGHTING
            = "INSERT INTO Sighting (LocationID, SightingDate, SightingTime) "
            + "VALUES(?, ?, ?)";

    private static final String SQL_UPDATE_SIGHTING
            = "UPDATE Sighting SET "
            + "LocationID = ?, SightingDate = ?, SightingTime = ? "
            + "WHERE SightingID = ?";

    private static final String SQL_DELETE_SIGHTING
            = "DELETE FROM Sighting WHERE SightingID = ?";

    private static final String SQL_SELECT_SIGHTING
            = "SELECT SightingID, LocationID, SightingDate, SightingTime "
            + "FROM Sighting "
            + "WHERE SightingID = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "SELECT * FROM Sighting";

    private static final String SQL_SELECT_SIGHTINGS_BY_LOCATION
            = "SELECT * FROM Sighting WHERE LocationID = ?";

    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "SELECT * FROM Sighting WHERE SightingDate = ?";

    private final JdbcTemplate jdbcTemplate;

    public SightingDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {

        Timestamp optionalTime = null;

        if (sighting.getTimeOccurred() != null) {
            optionalTime = Timestamp.valueOf(sighting.getTimeOccurred());
        }
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getLocation().getLocationId(),
                Date.valueOf(sighting.getDateOccurred()),
                optionalTime
        );

        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(id);

        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {

        Timestamp optionalTime = null;
        
        if (sighting.getTimeOccurred() != null) {
            optionalTime = Timestamp.valueOf(sighting.getTimeOccurred());
        }
        
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getLocation().getLocationId(),
                Date.valueOf(sighting.getDateOccurred()),
                optionalTime,
                sighting.getSightingId()
        );
    }

    @Override
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_SIGHTING, new SightingMapper(), sightingId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public List<Sighting> getSightingsByLocation(int locationId) {
        return jdbcTemplate.query(
                SQL_SELECT_SIGHTINGS_BY_LOCATION,
                new SightingMapper(), locationId);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return jdbcTemplate.query(
                SQL_SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), Date.valueOf(date));
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();

            sighting.setSightingId(rs.getInt("SightingID"));

            // For Location, just get the ID.
            // Service will build the rest if it needs to.
            Location l = new Location();
            l.setLocationId(rs.getInt("LocationID"));
            sighting.setLocation(l);

            sighting.setDateOccurred(rs.getDate("SightingDate")
                    .toLocalDate());

            Timestamp time = rs.getTimestamp("SightingTime");
            if (!rs.wasNull()) {
                sighting.setTimeOccurred(rs.getTimestamp("SightingTime")
                        .toLocalDateTime());
            }

            return sighting;
        }

    }

}
