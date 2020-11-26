package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.ShortcutReq;
import ru.job4j.model.ShortcutResp;
import ru.job4j.repository.ShortcutReqRepository;
import ru.job4j.repository.ShortcutRespRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ShortcutService {
    private final ShortcutReqRepository repositoryReq;
    private final ShortcutRespRepository repositoryResp;

    public ShortcutService(ShortcutReqRepository repositoryReq, ShortcutRespRepository repositoryResp) {
        this.repositoryReq = repositoryReq;
        this.repositoryResp = repositoryResp;
    }

    public ShortcutResp saveUrlAndCreateKey(ShortcutReq shortcutReq) {
        String key = null;
        try {
            key = URLEncoder.encode(shortcutReq.getUrl(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ShortcutResp shortcutResp = new ShortcutResp();
        shortcutResp.setCode(key);
        ShortcutReq req = new ShortcutReq(shortcutResp);
        req.setUrl(shortcutReq.getUrl());
        repositoryResp.save(shortcutResp);
        repositoryReq.save(req);
        return shortcutResp;
    }
}
