package com.sg.herotracker;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.Location;
import com.sg.herotracker.dto.Sighting;
import com.sg.herotracker.service.HeroService;
import com.sg.herotracker.service.LocationService;
import com.sg.herotracker.service.OrganizationService;
import com.sg.herotracker.service.SightingService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/sighting"})
public class SightingController {

    HeroService heroService;
    SightingService sightingService;
    LocationService locationService;
    OrganizationService orgService;

    public SightingController(
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
    public String doSightingStuff(Model model, HttpServletRequest request) {
        model.addAttribute("heroSightings",
                sightingService.getHeroSightingRelationshipsBySighting(0));
        model.addAttribute("heroes", heroService.getAllHeroes(10));
        model.addAttribute("locations", locationService.getAllLocations());
        return "sighting";
    }

    @RequestMapping(value="/reportSighting", method = RequestMethod.POST)
    public String createSighting(Model model, HttpServletRequest request) {
    int locationId = Integer.parseInt(request.getParameter("locationId"));
    Location l = locationService.getLocationById(locationId);
    
    String date = request.getParameter("sightingDate");
    String time = request.getParameter("sightingTime");
    LocalDate sightingDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
    LocalTime sightingTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
    
//    Sighting s = new Sighting();
//    s.setLocation(l);
//    s.setDateOccurred();
//    s.setTimeOccurred();
//        sightingService.createNewSighting()
        return "home";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.POST)
    public String deleteSighting(HttpServletRequest request) {
        int sightingId = Integer.parseInt(request.getParameter("sightingId"));
        sightingService.deleteSighting(sightingId);
        return "redirect:/sighting";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(Model model) {
        return "redirect:/sighting";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    public String doEdit(@ModelAttribute Sighting sighting, Model model, HttpServletRequest request) {
        return "redirect:/sighting";
    }

    @RequestMapping(value = "/addHeroSighting", method = RequestMethod.POST)
    public String addHeroSighting(HttpServletRequest request) {
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int sightingId = Integer.parseInt(request.getParameter("sightingId"));
        Hero h = heroService.getHeroById(heroId);
        Sighting s = sightingService.getSightingById(sightingId);
        sightingService.linkHeroToSighting(h, s);

        return "redirect:/sighting?sightingId=" + sightingId;
    }

    @RequestMapping(value = "/removeHeroSighting", method = RequestMethod.POST)
    public String removeHeroSighting(HttpServletRequest request) {
        int heroSightingId = Integer.parseInt(request.getParameter("heroSightingId"));
        sightingService.removeHeroFromSighting(heroSightingId);
        return "redirect:/sighting";
    }

}
