package com.rlima.demo.web.controller;

import com.rlima.demo.domain.Emissor;
import com.rlima.demo.repository.PromocaoRepository;
import com.rlima.demo.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class NotificacaoController {

    @Autowired
    private PromocaoRepository promocaoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/promocao/notificacao")
    public SseEmitter enviarNotificacao() throws IOException {
        SseEmitter emitter = new SseEmitter(0L);

        Emissor emissor = new Emissor(emitter, getPromocaoMaisRecente());
        notificacaoService.onOpen(emissor);
        notificacaoService.addEmissor(emissor);

        emissor.getSse().onCompletion( () -> notificacaoService.removeEmissor(emissor) );

        return emissor.getSse();
    }

    private LocalDateTime getPromocaoMaisRecente() {
        return promocaoRepository.findPromocaoMaisRecente();
    }
}
