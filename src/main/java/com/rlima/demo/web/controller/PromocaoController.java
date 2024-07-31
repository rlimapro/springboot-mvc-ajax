package com.rlima.demo.web.controller;

import com.rlima.demo.domain.Categoria;
import com.rlima.demo.domain.Promocao;
import com.rlima.demo.repository.CategoriaRepository;
import com.rlima.demo.repository.PromocaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
    private static Logger log = LoggerFactory.getLogger(Promocao.class);

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private PromocaoRepository promocaoRepository;

    @GetMapping("/add")
    public String abrirCadastro() {
        return "promo-add";
    }

    @PostMapping("/save")
    public ResponseEntity<Promocao> salvarPromocao(Promocao promocao) {
        log.info("Promocao {}", promocao.toString());
        promocao.setDataCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }
}
