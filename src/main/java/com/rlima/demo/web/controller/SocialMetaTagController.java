package com.rlima.demo.web.controller;

import com.rlima.demo.domain.SocialMetaTag;
import com.rlima.demo.service.SocialMetaTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/meta")
public class SocialMetaTagController {
    @Autowired
    private SocialMetaTagService service;

    @PostMapping("/info")
    public ResponseEntity<SocialMetaTag> getDataFromURL(@RequestParam("url") String url) {
        SocialMetaTag socialMetaTag = service.getSocialMetaTagByURL(url);
        return socialMetaTag != null
                ? ResponseEntity.ok(socialMetaTag)
                : ResponseEntity.notFound().build();
    }
}
