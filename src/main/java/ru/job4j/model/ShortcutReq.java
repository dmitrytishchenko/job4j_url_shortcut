package ru.job4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "urls")
public class ShortcutReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String url;
    @JsonIgnore
    @OneToOne
    private ShortcutResp codes;

    public ShortcutReq() {
    }

    public ShortcutReq(ShortcutResp codes) {
        this.codes = codes;
    }

    @Override
    public String toString() {
        return "ShortcutReq{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
