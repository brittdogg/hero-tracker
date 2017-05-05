package com.sg.herotracker;

import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.service.HeroService;
import com.sg.herotracker.service.LocationService;
import com.sg.herotracker.service.OrganizationService;
import com.sg.herotracker.service.SightingService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/location"})
public class LocationController {

    HeroService heroService;
    SightingService sightingService;
    LocationService locationService;
    OrganizationService orgService;

    public LocationController(
            HeroService heroService,
            SightingService sightingService,
            LocationService locationService,
            OrganizationService orgService) {
        this.heroService = heroService;
        this.sightingService = sightingService;
        this.locationService = locationService;
        this.orgService = orgService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doLocationStuff(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "location";
    }
    
    @RequestMapping(value="/createLocation", method=RequestMethod.POST)
    public String createLocation(Model model, HttpServletRequest request) {
        
        Address a = new Address();
        a.setStreet1(request.getParameter("locStreet1"));
        a.setStreet2(request.getParameter("locStreet2"));
        a.setCity(request.getParameter("locCity"));
        a.setState(request.getParameter("locState"));
        a.setZip(request.getParameter("locZip"));
        a = locationService.createAddress(a);
        
        float latDeg = Float.parseFloat(request.getParameter("latDegrees"));
        float longDeg = Float.parseFloat(request.getParameter("longDegrees"));
        
        Location l = new Location();
        l.setAddress(a);
        l.setName(request.getParameter("locName"));
        l.setDescription(request.getParameter("locDescription"));
        l.setLatitudeDegrees(latDeg);
        l.setLatitudeDirection(request.getParameter("latDirection"));
        l.setLongitudeDegrees(longDeg);
        l.setLongitudeDirection(request.getParameter("longDirection"));
        l = locationService.createLocation(l);
        
        return "redirect:/location";
    }
    
    @RequestMapping(value="/deleteLocation", method=RequestMethod.POST)
    public String deleteLocation(Model model, HttpServletRequest request) {
        int locId = Integer.parseInt(request.getParameter("locationId"));
        int addressId = locationService.getLocationById(locId).getAddress().getAddressId();
        locationService.deleteLocation(locId);
        locationService.deleteAddress(addressId);
        return "redirect:/location";
    }
}
