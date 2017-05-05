package com.sg.herotracker;

import com.sg.herotracker.dto.HeroSighting;
import com.sg.herotracker.service.HeroService;
import com.sg.herotracker.service.LocationService;
import com.sg.herotracker.service.OrganizationService;
import com.sg.herotracker.service.SightingService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/"})
public class HomeController {

    HeroService heroService;
    SightingService sightingService;
    LocationService locationService;
    OrganizationService orgService;

    public HomeController(
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
    public String goHome(Map<String, Object> model) {
        return doHomeStuff(model);
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String doHomeStuff(Map<String, Object> model) {

        List<HeroSighting> recentSightings
                = sightingService.getRecentHeroSightings(10);
        
        model.put("sightings", recentSightings);
        // TODO I wanted to group these by sighting date/time.
        return "home";
    }
}
