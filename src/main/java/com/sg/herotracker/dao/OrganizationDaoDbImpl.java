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
public class OrganizationDaoDbImpl implements OrganizationDao {

    private static final String SQL_INSERT_ORGANIZATION = 
            "INSERT INTO Organization (OrgName, Description, HQAddressID, "
            + "ContactName, ContactPhone, ContactEmail) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE_ORGANIZATION = 
            "UPDATE Organization SET OrgName = ?, Description = ?, "
            + "HQAddressID = ?, ContactName = ?, ContactPhone = ?, "
            + "ContactEmail = ? "
            + "WHERE OrgID = ?";
    
    private static final String SQL_DELETE_ORGANIZATION = 
            "DELETE FROM Organization WHERE OrgID = ?";
    
    private static final String SQL_SELECT_ORGANIZATION = 
            "SELECT * FROM Organization WHERE OrgID = ?";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_HERO = 
            "SELECT * FROM Organization o "
            + "JOIN OrganizationHero oh ON oh.OrgID = o.OrgID "
            + "WHERE oh.HeroID = ?";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS = 
            "SELECT * FROM Organization";
    
    
    private final JdbcTemplate jdbcTemplate;

    public OrganizationDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization addOrganization(Organization org) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                org.getName(),
                org.getDescription(),
                org.getAddress().getAddressId(),
                org.getContactName(),
                org.getContactPhone(),
                org.getContactEmail());
        
        int orgId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(orgId);
        
        return org;
    }

    @Override
    public void updateOrganization(Organization org) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                org.getName(),
                org.getDescription(),
                org.getAddress().getAddressId(),
                org.getContactName(),
                org.getContactPhone(),
                org.getContactEmail(),
                org.getOrgId()
        );
    }

    @Override
    public void deleteOrganization(int orgId) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, orgId);
    }

    @Override
    public Organization getOrganizationById(int orgId) {
        try {
            return jdbcTemplate.queryForObject(
                SQL_SELECT_ORGANIZATION, new OrganizationMapper(), orgId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizationsForHero(int heroId) {
        return jdbcTemplate.query(
                SQL_SELECT_ORGANIZATIONS_BY_HERO, new OrganizationMapper(), heroId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }
    
    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            
            org.setOrgId(rs.getInt("OrgID"));
            org.setName(rs.getString("OrgName"));
            org.setDescription(rs.getString("Description"));
            
            // only ID for address.
            Address a = new Address();
            a.setAddressId(rs.getInt("HQAddressID"));
            org.setAddress(a);
            
            org.setContactName(rs.getString("ContactName"));
            org.setContactPhone(rs.getString("ContactPhone"));
            org.setContactEmail(rs.getString("ContactEmail"));
            
            return org;
        }

    }
    

}
