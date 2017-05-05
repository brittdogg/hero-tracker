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
public interface OrganizationDao {
    
    Organization addOrganization(Organization org);
    
    void updateOrganization(Organization org);
    
    void deleteOrganization(int orgId);
    
    Organization getOrganizationById(int orgId);
    
    List<Organization> getAllOrganizationsForHero(int heroId);
    
    List<Organization> getAllOrganizations();
    
}
