package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Link;
import ru.job4j.model.Site;
import ru.job4j.service.SiteService;

import java.util.List;

@RestController
public class MainController {

    private final SiteService siteService;
@Autowired
    public MainController(SiteService siteService) {
        this.siteService = siteService;
    }


    @PostMapping("/registration")
    public ResponseEntity<Site> create(@RequestBody Link link) {
    ResponseEntity<Site> result;
   Site site = siteService.getAll()
           .stream()
           .filter(s -> s.getLogin().equals(link.getSite()))
           .findFirst()
           .orElse(null);
    if (site == null) {
        site = new Site();
        site.setLogin(link.getSite());
        site.setRegistration("true");
        result = new ResponseEntity<Site>(site, HttpStatus.CREATED);
    } else {
        site.setRegistration("false");
        result = new ResponseEntity<Site>(site, HttpStatus.BAD_REQUEST);
    }
        siteService.create(site);
        return result;
    }

    @GetMapping(value = "/sites")
    public ResponseEntity<List<Site>> read() {
        final List<Site> sites = siteService.getAll();
        return sites != null &&  !sites.isEmpty()
                ? new ResponseEntity<List<Site>>(sites, HttpStatus.OK)
                : new ResponseEntity<List<Site>>(HttpStatus.NOT_FOUND);
    }
}
