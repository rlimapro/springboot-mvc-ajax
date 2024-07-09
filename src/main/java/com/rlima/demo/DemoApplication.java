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
        SocialMetaTag og = service.getOpenGraphByURL("https://www.devmedia.com.br/primeiros-passos-com-o-spring-boot/33654");
        System.out.println(og);
    }
}
