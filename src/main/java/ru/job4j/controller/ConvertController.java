package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.job4j.model.ShortcutReq;
import ru.job4j.model.ShortcutResp;
import ru.job4j.service.ShortcutService;

import java.util.List;

@RestController
public class ConvertController {
    private static Logger logger = LoggerFactory.getLogger(ConvertController.class);
    private ShortcutService shortcutService;

    public ConvertController(ShortcutService shortcutService) {
        this.shortcutService = shortcutService;
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public ResponseEntity<ShortcutResp> convert(@RequestBody ShortcutReq shortcutReq) {
        ShortcutResp shortcutResp = shortcutService.saveUrlAndCreateKey(shortcutReq);
        return new ResponseEntity<ShortcutResp>(shortcutResp, HttpStatus.OK);
    }

    @GetMapping(value = "/redirect/{code}")
    public RedirectView goToUrlByCode(@PathVariable (name = "code") String code) {
        logger.info("The code getting by request " + code);
        String url = shortcutService.getUrlByCode(code);
        logger.info("The URL that getting by code " + url);
        RedirectView rv = new RedirectView();
        rv.setUrl(url);
        return rv;
    }

    @GetMapping(value = "/statistic")
    @ResponseBody
    public List<ShortcutReq> getStatistic() {
        return shortcutService.findAllShortcutReq();
    }
}
