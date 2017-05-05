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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bdogg
 */
public class SuperPowerDaoDbImpl implements SuperPowerDao {
    
    private static final String SQL_INSERT_POWER =
            "INSERT INTO SuperPower (PowerName, Description) "
            + "VALUES (?, ?)";
    
    private static final String SQL_UPDATE_POWER =
            "UPDATE SuperPower SET PowerName = ?, Description = ? "
            + "WHERE SuperPowerID = ?";
    
    private static final String SQL_DELETE_POWER =
            "DELETE FROM SuperPower WHERE SuperPowerID = ?";
    
    private static final String SQL_SELECT_POWER =
            "SELECT * FROM SuperPower WHERE SuperPowerID = ?";
    
    private static final String SQL_SELECT_ALL_POWERS =
            "SELECT * FROM SuperPower";

    private final JdbcTemplate jdbcTemplate;

    public SuperPowerDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower addSuperPower(SuperPower power) {
        jdbcTemplate.update(SQL_INSERT_POWER,
                power.getName(),
                power.getDescription());
        
        int powerId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        
        power.setSuperPowerId(powerId);
        
        return power;
    }

    @Override
    public void updateSuperPower(SuperPower power) {
        jdbcTemplate.update(SQL_UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getSuperPowerId());
    }

    @Override
    public void deleteSuperPower(int superPowerId) {
        jdbcTemplate.update(SQL_DELETE_POWER, superPowerId);
    }

    @Override
    public SuperPower getSuperPowerById(int superPowerId) {
        try {
            return jdbcTemplate.queryForObject(
                SQL_SELECT_POWER, new SuperPowerMapper(), superPowerId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_POWERS, new SuperPowerMapper());
    }
    
    private static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int i) throws SQLException {
            SuperPower power = new SuperPower();
            
            power.setSuperPowerId(rs.getInt("SuperPowerID"));
            power.setName(rs.getString("PowerName"));
            power.setDescription(rs.getString("Description"));
            
            return power;
        }

    }
    

}
