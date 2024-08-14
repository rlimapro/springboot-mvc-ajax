package com.rlima.demo.repository;

import com.rlima.demo.domain.Promocao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
    List<Promocao> findAllByOrderByDataCadastroDesc(Pageable pageable);

    @Query("SELECT p.likes from Promocao p where p.id = :id")
    int findLikesById(@Param("id") Long id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("UPDATE Promocao p SET p.likes = p.likes + 1 WHERE p.id = :id")
    void updateLikes(@Param("id") Long id);
}
