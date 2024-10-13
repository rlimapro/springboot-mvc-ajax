package com.rlima.demo.dto;

import com.rlima.demo.domain.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class PromocaoDTO {
    @NotNull
    private Long id;

    @NotBlank(message = "Um título é requerido")
    private String titulo;

    private String descricao;

    @NotBlank(message = "Uma imagem é requerida")
    private String linkImagem;

    @NotNull(message = "O preço é requerido")
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    private BigDecimal preco;

    @NotNull(message = "Uma categoria é requerida")
    private Categoria categoria;

    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Um título é requerido") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "Um título é requerido") String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public @NotBlank(message = "Uma imagem é requerida") String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(@NotBlank(message = "Uma imagem é requerida") String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public @NotNull(message = "O preço é requerido") BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "O preço é requerido") BigDecimal preco) {
        this.preco = preco;
    }

    public @NotNull(message = "Uma categoria é requerida") Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull(message = "Uma categoria é requerida") Categoria categoria) {
        this.categoria = categoria;
    }
}
