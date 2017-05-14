package com.sg.herotracker;

import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.SuperPower;
import com.sg.herotracker.service.HeroService;
import com.sg.herotracker.service.LocationService;
import com.sg.herotracker.service.OrganizationService;
import com.sg.herotracker.service.SightingService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/hero"})
public class HeroController {

    HeroService heroService;
    SightingService sightingService;
    LocationService locationService;
    OrganizationService orgService;

    public HeroController(
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
    public String doHeroStuff(Map<String, Object> model) {
        model.put("heroes", heroService.getAllHeroes(10));
        model.put("powers", heroService.getAllSuperPowers());
        return "hero";
    }

    @RequestMapping(value="/createHero", method = RequestMethod.POST)
    public String createHero(HttpServletRequest request) {
        Hero newHero = new Hero();
        newHero.setName(request.getParameter("heroName"));
        newHero.setDescription(request.getParameter("heroDescription"));
        int powerId = Integer.parseInt(request.getParameter("superPower"));
        newHero.setSuperPower(
                heroService.getSuperPowerById(powerId)
        );
        
        heroService.createNewHero(newHero);
        return "redirect:/hero";
    }
    
    @RequestMapping(value="/deleteHero", method=RequestMethod.POST)
    public String deleteHero(HttpServletRequest request) {
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        heroService.deleteHero(heroId);
        return "redirect:/hero";
    }

    //TODO Use @ModelAttribute here.
    @RequestMapping(value="/editHero", method=RequestMethod.GET)
    public String showHeroEdit(HttpServletRequest request, Map<String, Object> model) {
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        Hero hero = heroService.getHeroById(heroId);
        model.put("hero", hero);
        model.put("powers", heroService.getAllSuperPowers());
        model.put("memberships", orgService.getMembershipsForHero(heroId));
        model.put("orgs", orgService.getAllOrgs());
        model.put("heroSightings",
                sightingService.getHeroSightingRelationshipsByHero(heroId));
        return "editHero";
    }
    
    @RequestMapping(value="/doEdit", method=RequestMethod.POST)
    public String editHero(@ModelAttribute("hero") Hero hero,
            BindingResult result, HttpServletRequest request) {
        int powerId = Integer.parseInt(request.getParameter("superPower"));
        SuperPower power = heroService.getSuperPowerById(powerId);
        hero.setSuperPower(power);
        heroService.updateHeroDetails(hero);
        return "redirect:/hero";
    }
    
}
