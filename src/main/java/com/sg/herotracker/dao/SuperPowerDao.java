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
public interface SuperPowerDao {
    
    SuperPower addSuperPower(SuperPower power);
    
    void updateSuperPower(SuperPower power);
    
    void deleteSuperPower(int superPowerId);
    
    SuperPower getSuperPowerById(int superPowerId);
    
    // This might be useful in the case where you have a limited number of
    // super powers that you want people to pick from.
    List<SuperPower> getAllSuperPowers();
    
}
