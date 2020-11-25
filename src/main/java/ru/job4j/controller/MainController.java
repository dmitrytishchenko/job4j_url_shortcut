package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.AuthenticationResponce;
import ru.job4j.model.Link;
import ru.job4j.model.Site;
import ru.job4j.security.JWTUtil;
import ru.job4j.service.MyUserDetailsService;
import ru.job4j.service.SiteService;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtTokenUtil;

    private final SiteService siteService;

    public MainController(SiteService siteService) {
        this.siteService = siteService;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponce> createAuthenticationToken(
            @RequestBody Site site) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(site.getLogin(), site.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect login or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(site.getLogin());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponce(jwt));
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
        return sites != null && !sites.isEmpty()
                ? new ResponseEntity<List<Site>>(sites, HttpStatus.OK)
                : new ResponseEntity<List<Site>>(HttpStatus.NOT_FOUND);
    }
}
