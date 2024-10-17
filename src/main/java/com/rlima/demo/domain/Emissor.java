package com.rlima.demo.domain;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.UUID;

public class Emissor {
    private String id = UUID.randomUUID().toString();
    private SseEmitter sse;
    private LocalDateTime ultimaData;

    public Emissor(SseEmitter sse, LocalDateTime ultimaData) {
        this.sse = sse;
        this.ultimaData = ultimaData;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getUltimaData() {
        return ultimaData;
    }

    public void setUltimaData(LocalDateTime ultimaData) {
        this.ultimaData = ultimaData;
    }

    public SseEmitter getSse() {
        return sse;
    }

    public void setSse(SseEmitter sse) {
        this.sse = sse;
    }
}
