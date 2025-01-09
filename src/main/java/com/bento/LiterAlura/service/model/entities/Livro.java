package com.bento.LiterAlura.service.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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

    @Column(name = "data_nascimento_autor")
    private LocalDate dataNascimentoAutor;

    @Column(name = "data_falecimento_autor", nullable = true) // Pode ser nulo caso o autor esteja vivo
    private LocalDate dataFalecimentoAutor;

    // Construtor padrão necessário para o Hibernate
    public Livro() {
    }

    // Construtor com parâmetros
    public Livro(String titulo, String autor, String idioma, Integer numeroDownloads, LocalDate dataNascimentoAutor, LocalDate dataFalecimentoAutor) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.dataNascimentoAutor = dataNascimentoAutor;
        this.dataFalecimentoAutor = dataFalecimentoAutor;
    }
}
