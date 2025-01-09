package com.bento.LiterAlura.service.model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("authors")
    private AuthorDTO[] authors;

    @JsonProperty("language")
    private String idioma;

    @JsonProperty("download_count")
    private Integer numeroDownloads;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorDTO {
        @JsonProperty("name")
        private String name;

        @JsonProperty("birth_year")
        private Integer birthYear;

        @JsonProperty("death_year")
        private Integer deathYear;
    }

    // Métodos auxiliares para acessar as datas do primeiro autor, se disponível
    public String getAutor() {
        return (authors != null && authors.length > 0) ? authors[0].getName() : "Autor desconhecido";
    }

    public LocalDate getDataNascimentoAutor() {
        return (authors != null && authors.length > 0 && authors[0].getBirthYear() != null)
                ? LocalDate.of(authors[0].getBirthYear(), 1, 1)
                : null;
    }

    public LocalDate getDataFalecimentoAutor() {
        return (authors != null && authors.length > 0 && authors[0].getDeathYear() != null)
                ? LocalDate.of(authors[0].getDeathYear(), 1, 1)
                : null;
    }
}
