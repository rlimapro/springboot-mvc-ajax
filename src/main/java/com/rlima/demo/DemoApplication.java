package com.rlima.demo;

import com.rlima.demo.domain.SocialMetaTag;
import com.rlima.demo.service.SocialMetaTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private SocialMetaTagService service;

    @Override
    public void run(String... args) throws Exception {
        SocialMetaTag tag = service.getTwitterCardByURL("https://www.gkinfostore.com.br/vzf0t2wzq-ssd-960gb-a400-kingston-25-sata-iii-blister-sa400s37960g");
        System.out.println(tag);
    }
}
