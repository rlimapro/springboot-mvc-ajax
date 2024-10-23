package com.rlima.demo.service;

import com.rlima.demo.domain.Emissor;
import com.rlima.demo.repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@EnableScheduling
@Service
public class NotificacaoService {
    @Autowired
    private PromocaoRepository promocaoRepository;

    private CopyOnWriteArrayList<Emissor> emissores = new CopyOnWriteArrayList<>();

    public void onOpen(Emissor emissor) throws IOException {
        emissor.getSse().send(SseEmitter.event()
                .data(" ")
                .id(emissor.getId()));
    }

    public void addEmissor(Emissor emissor) {
        this.emissores.add(emissor);
    }

    public void removeEmissor(Emissor emissor) {
        this.emissores.remove(emissor);
    }

    @Scheduled(fixedRate = 35000)
    public void notificar() {
        List<Emissor> emissoresErrors = new ArrayList<>();
        this.emissores.forEach(emissor -> {
            try {
                Map<String, Object> map =
                        promocaoRepository.countAndMaxNovasPromocoesByDataCadastro(emissor.getUltimaData());

                long count = (long) map.get("count");

                if (count > 0) {
                    emissor.setUltimaData((LocalDateTime) map.get("lastDate"));
                    emissor.getSse().send(SseEmitter.event()
                            .data(count)
                            .id(emissor.getId()));
                }
            } catch (IOException e) {
                emissoresErrors.add(emissor);
            }
        });
        this.emissores.removeAll(emissoresErrors);
    }

    public CopyOnWriteArrayList<Emissor> getEmissores() {
        return emissores;
    }


}
