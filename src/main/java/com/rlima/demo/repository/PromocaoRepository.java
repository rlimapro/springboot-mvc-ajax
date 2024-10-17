package com.rlima.demo.repository;

import com.rlima.demo.domain.Promocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
    List<Promocao> findAllByOrderByDataCadastroDesc(Pageable pageable);
    Page<Promocao> findBySiteOrderByDataCadastroDesc(String site, Pageable pageable);
    Page<Promocao> findByPrecoLessThan(BigDecimal preco, Pageable pageable);

    @Query("SELECT p FROM Promocao p WHERE p.titulo like %:search% " +
            "or p.site like %:search% " +
            "or p.categoria.titulo like %:search%")
    Page<Promocao> findByTituloOrSiteOrCategoria(@Param("search") String search, Pageable pageable);

    @Query("SELECT DISTINCT p.site FROM Promocao p WHERE p.site like %:site%")
    List<String> findSiteByTerm(@Param("site") String site);

    @Query("SELECT p.likes FROM Promocao p WHERE p.id = :id")
    int findLikesById(@Param("id") Long id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("UPDATE Promocao p SET p.likes = p.likes + 1 WHERE p.id = :id")
    void updateLikes(@Param("id") Long id);

    @Query("SELECT MAX(p.dataCadastro) FROM Promocao p")
    LocalDateTime findPromocaoMaisRecente();
}
