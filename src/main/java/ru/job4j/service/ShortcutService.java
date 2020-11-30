package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.ShortcutReq;
import ru.job4j.model.ShortcutResp;
import ru.job4j.repository.ShortcutReqRepository;
import ru.job4j.repository.ShortcutRespRepository;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class ShortcutService {
    private final ShortcutReqRepository repositoryReq;
    private final ShortcutRespRepository repositoryResp;

    public ShortcutService(ShortcutReqRepository repositoryReq,
                           ShortcutRespRepository repositoryResp) {
        this.repositoryReq = repositoryReq;
        this.repositoryResp = repositoryResp;
    }

    public ShortcutResp saveUrlAndCreateKey(ShortcutReq shortcutReq) {
        String key = null;
        try {
            key = Base64.getUrlEncoder()
                    .encodeToString(shortcutReq.getUrl()
                            .getBytes(StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ShortcutResp shortcutResp = new ShortcutResp();
        shortcutResp.setCode(key);
        ShortcutReq req = new ShortcutReq(shortcutResp);
        req.setTotal(0L);
        req.setUrl(shortcutReq.getUrl());
        repositoryResp.save(shortcutResp);
        repositoryReq.save(req);
        return shortcutResp;
    }

    public String getUrlByCode(String code) {
        long idByCode = 0;
        String url = null;
        for (ShortcutResp resp : repositoryResp.findAll()) {
            if (resp.getCode().equals(code)) {
                idByCode = resp.getId();
                break;
            }
        }
        for (ShortcutReq req : repositoryReq.findAll()) {
            if (req.getId().equals(idByCode)) {
                url = req.getUrl();
                Long total = req.getTotal();
                total++;
                req.setTotal(total);
                repositoryReq.save(req);
            }
        }
        return url;
    }

    public List<ShortcutReq> findAllShortcutReq() {
        return (List<ShortcutReq>) repositoryReq.findAll();
    }
}
