package com.sg.herotracker;

import com.sg.herotracker.dto.Address;
import com.sg.herotracker.dto.Hero;
import com.sg.herotracker.dto.Organization;
import com.sg.herotracker.service.HeroService;
import com.sg.herotracker.service.LocationService;
import com.sg.herotracker.service.OrganizationService;
import com.sg.herotracker.service.SightingService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/organization"})
public class OrganizationController {

    HeroService heroService;
    SightingService sightingService;
    LocationService locationService;
    OrganizationService orgService;

    public OrganizationController(
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
    public String doOrganizationStuff(Model model) {
        model.addAttribute("orgs", orgService.getAllOrgs());
        model.addAttribute("locations", locationService.getAllLocations());
        return "organization";
    }

    @RequestMapping(value = "/createOrg", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request) {
        int addressId = Integer.parseInt(request.getParameter("addressId"));
        Address a = locationService.getAddressById(addressId);

        Organization o = new Organization();
        o.setAddress(a);
        o.setName(request.getParameter("orgName"));
        o.setDescription(request.getParameter("orgDescription"));
        o.setContactName(request.getParameter("orgContact"));
        o.setContactPhone(request.getParameter("orgPhone"));
        o.setContactEmail(request.getParameter("orgEmail"));
        o = orgService.createNewOrganization(o);

        return "redirect:/organization";
    }

    @RequestMapping(value = "/deleteOrg", method = RequestMethod.POST)
    public String deleteOrganization(HttpServletRequest request) {
        int orgId = Integer.parseInt(request.getParameter("orgId"));
        orgService.deleteOrganization(orgId);
        return "redirect:/organization";
    }

    @RequestMapping(value = "/editOrg", method = RequestMethod.GET)
    public String showOrgEdit(Model model, HttpServletRequest request) {
        int orgId = Integer.parseInt(request.getParameter("orgId"));
        Organization org = orgService.getOrganizationById(orgId);
        model.addAttribute("org", org);
        model.addAttribute("heroes", heroService.getAllHeroes(10));
        model.addAttribute("members", orgService.getMembersOfOrganization(orgId));
        model.addAttribute("locations", locationService.getAllLocations());
        return "editOrg";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    public String doEdit(@ModelAttribute Organization org, Model model, HttpServletRequest request) {
        int addressId = Integer.parseInt(request.getParameter("addressId"));
        Address a = locationService.getAddressById(addressId);
        org.setAddress(a);
        orgService.updateOrganizationDetails(org);
        return "redirect:/organization";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public String addMember(HttpServletRequest request) {
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int orgId = Integer.parseInt(request.getParameter("orgId"));
        Hero h = heroService.getHeroById(heroId);
        Organization o = orgService.getOrganizationById(orgId);
        orgService.addHeroToOrganization(o, h);

        String origin = request.getParameter("origin");
        if (origin.equals("hero")) {
            return "redirect:/hero/editHero?heroId=" + heroId;
        } else {
            return "redirect:/organization/editOrg?orgId=" + orgId;
        }
    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.POST)
    public String removeMember(HttpServletRequest request) {

        // remove the hero from the organization
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        orgService.removeHeroFromOrganization(memberId);

        // Decide where to return based on request origin
        String origin = request.getParameter("origin");
        if (origin.equals("org")) {
            int orgId = Integer.parseInt(request.getParameter("orgId"));
            return "redirect:/organization/editOrg?orgId=" + orgId;
        } else {
            int heroId = Integer.parseInt(request.getParameter("heroId"));
            return "redirect:/hero/editHero?heroId=" + heroId;
        }
    }
}
