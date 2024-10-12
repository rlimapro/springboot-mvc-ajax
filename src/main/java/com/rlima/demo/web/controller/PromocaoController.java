package com.rlima.demo.web.controller;

import com.rlima.demo.domain.Categoria;
import com.rlima.demo.domain.Promocao;
import com.rlima.demo.repository.CategoriaRepository;
import com.rlima.demo.repository.PromocaoRepository;
import com.rlima.demo.service.PromocaoDataTablesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/list")
    public String listarPromocoes(ModelMap model) {
        PageRequest pageRequest = PageRequest.of(0, 8);
        model.addAttribute("promocoes", promocaoRepository.findAllByOrderByDataCadastroDesc(pageRequest));
        return "promo-list";
    }

    @GetMapping("/list/ajax")
    public String listarCards(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "site", defaultValue = "") String site,
                              ModelMap model) {
        PageRequest pageRequest = PageRequest.of(page, 8);
        if(site.isEmpty()) {
            model.addAttribute("promocoes", promocaoRepository.findAllByOrderByDataCadastroDesc(pageRequest));
        } else {
            model.addAttribute("promocoes", promocaoRepository.findBySiteOrderByDataCadastroDesc(site, pageRequest));
        }
        return "promo-card";
    }

    @PostMapping("/save")
    public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for(FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        log.info("Promocao {}", promocao.toString());
        promocao.setDataCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        promocaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> adicionarLike(@PathVariable("id") Long id) {
        promocaoRepository.updateLikes(id);
        int likes = promocaoRepository.findLikesById(id);
        return ResponseEntity.ok().body(likes);
    }

    @GetMapping("/site")
    public ResponseEntity<?> autocompleteByTerm(@RequestParam("term") String term) {
        List<String> sites = promocaoRepository.findSiteByTerm(term);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/site/list")
    public String listarPorSite(@RequestParam("site") String site, ModelMap model) {
        PageRequest pageRequest = PageRequest.of(0, 8);
        model.addAttribute("promocoes", promocaoRepository.findBySiteOrderByDataCadastroDesc(site, pageRequest));
        return "promo-card";
    }

    @GetMapping("/tabela")
    public String showTable() {
        return "promo-datatables";
    }

    @GetMapping("/datatables/server")
    public ResponseEntity<?> dataTables(HttpServletRequest request) {
        Map<String, Object> data = new PromocaoDataTablesService().execute(promocaoRepository, request);
        return ResponseEntity.ok(data);
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }
}
