package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Site;
import ru.job4j.service.SiteService;

import java.util.List;

@RestController
public class MainController {

    private SiteService siteService;

    public MainController(SiteService siteService) {
        this.siteService = siteService;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/sites")
    public ResponseEntity<List<Site>> read() {
        final List<Site> sites = siteService.getAll();
        return sites != null && !sites.isEmpty()
                ? new ResponseEntity<List<Site>>(sites, HttpStatus.OK)
                : new ResponseEntity<List<Site>>(HttpStatus.NOT_FOUND);
    }
}
