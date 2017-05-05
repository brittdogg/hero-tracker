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
public class OrganizationHeroDaoDbImpl implements OrganizationHeroDao {

    private static final String SQL_INSERT_MEMBERSHIP
            = "INSERT INTO OrganizationHero (HeroID, OrgID) "
            + "VALUES (?, ?)";

    private static final String SQL_UPDATE_MEMBERSHIP
            = "UPDATE OrganizationHero SET "
            + "HeroID = ?, OrgID = ? "
            + "WHERE OrgHeroID = ?";

    private static final String SQL_DELETE_MEMBERSHIP
            = "DELETE FROM OrganizationHero WHERE OrgHeroID = ?";

    private static final String SQL_SELECT_ALL_MEMBERSHIPS
            = "SELECT oh.OrgHeroID, oh.OrgID, oh.HeroID, "
            + "o.OrgName, o.Description, o.HQAddressID, o.ContactName, "
            + "o.ContactPhone, o.ContactEmail, "
            + "h.HeroName, h.Description, h.SuperPowerID, "
            + "a.Street1, a.Street2, a.City, a.State, a.Zip, "
            + "sp.PowerName, sp.Description "
            + "FROM OrganizationHero oh "
            + "INNER JOIN Organization o ON o.OrgID = oh.OrgID "
            + "INNER JOIN Hero h ON oh.HeroID = h.HeroID "
            + "INNER JOIN SuperPower sp ON sp.SuperPowerID = h.SuperPowerID "
            + "INNER JOIN Address a ON o.HQAddressID = a.AddressID"
            ;

    private static final String SQL_SELECT_MEMBERSHIP
            = SQL_SELECT_ALL_MEMBERSHIPS + " WHERE oh.OrgHeroID = ?";

    private static final String SQL_SELECT_MEMBERSHIP_BY_HERO_AND_ORG
            = SQL_SELECT_ALL_MEMBERSHIPS
            + " WHERE oh.HeroID = ? AND oh.OrgID = ?";
    
    private static final String SQL_SELECT_MEMBERSHIPS_BY_HERO
            = SQL_SELECT_ALL_MEMBERSHIPS + " WHERE oh.HeroID = ?";
    
    private static final String SQL_SELECT_MEMBERSHIPS_BY_ORG
            = SQL_SELECT_ALL_MEMBERSHIPS + " WHERE oh.OrgID = ?";

    private final JdbcTemplate jdbcTemplate;

    public OrganizationHeroDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrganizationHero addMembership(OrganizationHero orgHero) {
        jdbcTemplate.update(SQL_INSERT_MEMBERSHIP,
                orgHero.getHero().getHeroId(),
                orgHero.getOrg().getOrgId()
        );

        int id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);

        orgHero.setOrgHeroId(id);

        return orgHero;
    }

    @Override
    public void updateMembership(OrganizationHero orgHero) {
        jdbcTemplate.update(SQL_UPDATE_MEMBERSHIP,
                orgHero.getHero().getHeroId(),
                orgHero.getOrg().getOrgId(),
                orgHero.getOrgHeroId()
        );
    }

    @Override
    public void deleteMembership(int orgHeroId) {
        jdbcTemplate.update(SQL_DELETE_MEMBERSHIP, orgHeroId);
    }

    @Override
    public OrganizationHero getMembership(int orgHeroId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_MEMBERSHIP, new OrganizationHeroMapper(),
                    orgHeroId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public OrganizationHero getMembershipByHeroAndOrgIds(int heroId, int orgId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_MEMBERSHIP_BY_HERO_AND_ORG,
                    new OrganizationHeroMapper(),
                    heroId, orgId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<OrganizationHero> getAllMemberships() {
        return jdbcTemplate.query(SQL_SELECT_ALL_MEMBERSHIPS,
                new OrganizationHeroMapper());
    }

    @Override
    public List<OrganizationHero> getMembershipsByHero(int heroId) {
        return jdbcTemplate.query(SQL_SELECT_MEMBERSHIPS_BY_HERO,
                new OrganizationHeroMapper(), heroId);
    }

    @Override
    public List<OrganizationHero> getMembershipsByOrg(int orgId) {
        return jdbcTemplate.query(SQL_SELECT_MEMBERSHIPS_BY_ORG,
                new OrganizationHeroMapper(), orgId);
    }

    private static final class OrganizationHeroMapper implements RowMapper<OrganizationHero> {

        @Override
        public OrganizationHero mapRow(ResultSet rs, int i) throws SQLException {
            OrganizationHero orgHero = new OrganizationHero();

            orgHero.setOrgHeroId(rs.getInt("oh.OrgHeroID"));
            
            // Build Organization with Address.
            Organization o = new Organization();
            o.setOrgId(rs.getInt("oh.OrgID"));
            o.setName(rs.getString("o.OrgName"));
            o.setDescription(rs.getString("o.Description"));
                Address a = new Address();
                a.setAddressId(rs.getInt("o.HQAddressID"));
                a.setStreet1(rs.getString("a.Street1"));
                a.setStreet2(rs.getString("a.Street2"));
                a.setCity(rs.getString("a.City"));
                a.setState(rs.getString("a.State"));
                a.setZip(rs.getString("a.Zip"));
            o.setAddress(a);
            o.setContactName(rs.getString("o.ContactName"));
            o.setContactPhone(rs.getString("o.ContactPhone"));
            o.setContactEmail(rs.getString("o.ContactEmail"));
            
            // Build Hero with SuperPower
            Hero h = new Hero();
            h.setHeroId(rs.getInt("oh.HeroID"));
            h.setName(rs.getString("h.HeroName"));
            h.setDescription(rs.getString("h.Description"));
                SuperPower p = new SuperPower();
                p.setSuperPowerId(rs.getInt("h.SuperPowerID"));
                p.setName(rs.getString("sp.PowerName"));
                p.setDescription(rs.getString("sp.Description"));
            h.setSuperPower(p);
            
            orgHero.setOrg(o);
            orgHero.setHero(h);

            return orgHero;
        }

    }

}
