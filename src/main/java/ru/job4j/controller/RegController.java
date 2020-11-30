package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Link;
import ru.job4j.model.Site;
import ru.job4j.service.SiteService;

@RestController
public class RegController {
    private SiteService siteService;

    public RegController(SiteService siteService) {
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
            result = new ResponseEntity<Site>(site, HttpStatus.OK);
        } else {
            site.setRegistration("false");
            result = new ResponseEntity<Site>(site, HttpStatus.OK);
        }
        siteService.create(site);
        return result;
    }
}
