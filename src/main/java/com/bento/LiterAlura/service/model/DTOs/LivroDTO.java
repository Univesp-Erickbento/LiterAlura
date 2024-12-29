    package com.bento.LiterAlura.service.model.DTOs;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos não mapeados no JSON
    public class LivroDTO {

        @JsonProperty("id")
        private String id; // Campo "id" retornado pela API

        @JsonProperty("title")
        private String titulo; // Nome traduzido para maior clareza

        @JsonProperty("author")
        private String autor;

        @JsonProperty("language")
        private String idioma;

        @JsonProperty("download_count")
        private Integer numeroDownloads;
    }
