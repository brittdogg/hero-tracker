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
public class AddressDaoDbImpl implements AddressDao {

    private static final String SQL_INSERT_ADDRESS
            = "INSERT INTO Address (Street1, Street2, City, State, Zip) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ADDRESS
            = "UPDATE Address SET "
            + "Street1 = ?, Street2 = ?, City = ?, State = ?, Zip = ? "
            + "WHERE AddressID = ?";

    private static final String SQL_DELETE_ADDRESS
            = "DELETE FROM Address WHERE AddressID = ?";

    private static final String SQL_SELECT_ADDRESS
            = "SELECT * FROM Address WHERE AddressID = ?";

    private static final String SQL_SELECT_ALL_ADDRESSES
            = "SELECT * FROM Address";

    private final JdbcTemplate jdbcTemplate;

    public AddressDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Address addAddress(Address address) {

        String optionalStreet2 = null;
        if (address.getStreet2() != null) {
            optionalStreet2 = address.getStreet2();
        }
        
        jdbcTemplate.update(SQL_INSERT_ADDRESS,
                address.getStreet1(),
                optionalStreet2,
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        int addressId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);

        address.setAddressId(addressId);

        return address;
    }

//    private String checkOptionalField(String maybeNull) {
//        if (maybeNull != null) {
//            return maybeNull;
//        } else {
//            return null;
//        }
//    }

    @Override
    public void updateAddress(Address address) {

        String optionalStreet2 = null;
        if (address.getStreet2() != null) {
            optionalStreet2 = address.getStreet2();
        }

        jdbcTemplate.update(SQL_UPDATE_ADDRESS,
                address.getStreet1(),
                optionalStreet2,
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getAddressId()
        );

    }

    @Override
    public void deleteAddress(int addressId) {
        jdbcTemplate.update(SQL_DELETE_ADDRESS, addressId);
    }

    @Override
    public Address getAddressById(int addressId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_ADDRESS, new AddressMapper(), addressId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Address> getAllAddresses() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ADDRESSES, new AddressMapper());
    }

    private static final class AddressMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int i) throws SQLException {

            Address address = new Address();

            address.setAddressId(rs.getInt("AddressID"));
            address.setStreet1(rs.getString("Street1"));
            address.setStreet2(rs.getString("Street2"));
            address.setCity(rs.getString("City"));
            address.setState(rs.getString("State"));
            address.setZip(rs.getString("Zip"));

            return address;
        }
    }

}
