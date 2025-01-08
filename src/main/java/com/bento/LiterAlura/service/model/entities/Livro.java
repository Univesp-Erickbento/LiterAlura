package com.bento.LiterAlura.service.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private String idioma;

    @Column(name = "numero_downloads")
    private Integer numeroDownloads;

    // Construtor padrão necessário para o Hibernate
    public Livro() {
    }

    // Construtor com parâmetros
    public Livro(String titulo, String autor, String idioma, Integer numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }
}
