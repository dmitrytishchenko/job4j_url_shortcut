package ru.job4j.model;

public class Link {
    private String site;

    public Link(String site) {
        this.site = site;
    }

    public Link() {
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Link{" +
                "siteName='" + site + '\'' +
                '}';
    }
}
