package com.rlima.demo.repository;

import com.rlima.demo.domain.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
    List<Promocao> findAllByOrderByDataCadastroDesc();
}
