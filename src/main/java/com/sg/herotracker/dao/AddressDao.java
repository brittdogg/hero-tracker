/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herotracker.dao;

import com.sg.herotracker.dto.*;
import java.util.List;

/**
 *
 * @author bdogg
 */
public interface AddressDao {
    
    Address addAddress(Address address);
    
    void updateAddress(Address address);
    
    void deleteAddress(int addressId);
    
    Address getAddressById(int addressId);
    
    // this is only for testing. isn't there some other way maybe?
    List<Address> getAllAddresses();
    
    // get addresses by other fields (for searching?)
    
}
