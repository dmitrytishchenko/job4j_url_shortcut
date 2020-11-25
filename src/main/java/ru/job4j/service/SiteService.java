package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Site;
import ru.job4j.repository.SiteRepository;

import java.util.List;

@Service
public class SiteService {
    private final SiteRepository repository;

    public SiteService(SiteRepository repository) {
        this.repository = repository;
    }

    public Site create(Site site) {
        repository.save(site);
        return site;
    }

    public Site findSiteByLogin(final String login) {
        Site site = getAll().stream().filter(s -> s.getLogin().equals(login)).findFirst().get();
        return site;
    }

    public List<Site> getAll() {
        return (List<Site>) repository.findAll();
    }

}
