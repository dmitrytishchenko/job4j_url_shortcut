package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.ShortcutReq;
import ru.job4j.model.ShortcutResp;
import ru.job4j.service.ShortcutService;

@RestController
public class ConvertController {
    private ShortcutService shortcutService;

    public ConvertController(ShortcutService shortcutService) {
        this.shortcutService = shortcutService;
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public ResponseEntity<ShortcutResp> convert(@RequestBody ShortcutReq shortcutReq) {
        ShortcutResp shortcutResp = shortcutService.saveUrlAndCreateKey(shortcutReq);
        return new ResponseEntity<ShortcutResp>(shortcutResp, HttpStatus.CREATED);
    }
}
