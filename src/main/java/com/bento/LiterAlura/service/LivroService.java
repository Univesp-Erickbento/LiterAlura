package com.bento.LiterAlura.service;

import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.DTOs.LivroDTO;
import com.bento.LiterAlura.service.model.entities.Livro;
import com.bento.LiterAlura.service.model.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LivroService(ConsumoApi consumoApi, LivroRepository livroRepository) {
        this.consumoApi = consumoApi;
        this.livroRepository = livroRepository;
    }

    public void buscarLivroPorTitulo(Scanner scanner) {
        System.out.println("Insira o nome do livro que deseja procurar:");
        String titulo = scanner.nextLine();

        // Faz a chamada à API
        String jsonResponse = consumoApi.obterDados("https://gutendex.com/books", "search=" + titulo);

        System.out.println("Resultado da busca por título (JSON): " + jsonResponse);

        try {
            // Parsing do JSON usando Jackson
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            if (results.isArray() && results.size() > 0) {
                JsonNode firstResult = results.get(0);
                LivroDTO livroDTO = objectMapper.treeToValue(firstResult, LivroDTO.class);

                // Cria um objeto Livro com os dados
                Livro livro = new Livro(livroDTO.getTitulo(), livroDTO.getAutor(), livroDTO.getIdioma(), livroDTO.getNumeroDownloads());

                // Salvar no banco de dados
                livroRepository.save(livro);

                System.out.println("Livro salvo no banco de dados!");
            } else {
                System.out.println("Nenhum livro encontrado com o título: " + titulo);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a resposta JSON: " + e.getMessage(), e);
        }
    }

    public void listarLivrosRegistrados() {
        livroRepository.findAll().forEach(livro ->
                System.out.println("Título: " + livro.getTitulo() +
                        ", Autor: " + livro.getAutor() +
                        ", Idioma: " + livro.getIdioma() +
                        ", Número de Downloads: " + livro.getNumeroDownloads())
        );
    }
}
