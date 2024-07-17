package com.rlima.demo.service;

import com.rlima.demo.domain.SocialMetaTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SocialMetaTagService {
    public SocialMetaTag getTwitterCardByURL(String URL) {
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
            tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
            tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
            tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
            tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public SocialMetaTag getOpenGraphByURL(String URL) {
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
            tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
            tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
            tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
            tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tag;
    }
}
