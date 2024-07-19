package com.rlima.demo.service;

import com.rlima.demo.domain.SocialMetaTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class SocialMetaTagService {
    private static final Logger log = LoggerFactory.getLogger(SocialMetaTag.class);

    public SocialMetaTag getSocialMetaTagByURL(String url) {
        SocialMetaTag twitter = getTwitterCardByURL(url);
        if(!isEmpty(twitter))
            return twitter;

        SocialMetaTag openGraph = getOpenGraphByURL(url);
        if(!isEmpty(openGraph))
            return openGraph;

        return null;
    }

    private SocialMetaTag getTwitterCardByURL(String URL) {
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
            tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content").replace("@", ""));
            tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
            tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
            tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
        return tag;
    }

    private SocialMetaTag getOpenGraphByURL(String URL) {
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
            tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
            tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
            tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
            tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
        return tag;
    }

    private boolean isEmpty(Object obj) {
        return Stream.of(obj).allMatch(Objects::isNull);
    }
}
