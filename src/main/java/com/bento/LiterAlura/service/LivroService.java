package com.bento.LiterAlura.service;

import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.DTOs.LivroDTO;
import com.bento.LiterAlura.service.model.entities.Livro;
import com.bento.LiterAlura.service.model.ConsumoApi;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(ConsumoApi consumoApi, LivroRepository livroRepository) {
        this.consumoApi = consumoApi;
        this.livroRepository = livroRepository;
    }

    public void buscarLivroPorTitulo(Scanner scanner) {
        // Pergunta ao usuário qual o título do livro
        System.out.println("Insira o nome do livro que deseja procurar:");
        String titulo = scanner.nextLine();

        // Pergunta ao usuário qual idioma deseja buscar
        System.out.println("Insira o idioma para realizar a busca (es, en, fr, pt):");
        String idioma = scanner.nextLine();

        // Busca o JSON da API externa com o título e idioma
        JsonNode apiResponse = consumoApi.buscarNaApiExterna("https://gutendex.com/books", "search=" + titulo + "&languages=" + idioma);

        // Exibir a resposta JSON para depuração
        System.out.println("Resultado da busca (JSON): " + apiResponse);

        // Verifica se há resultados
        JsonNode results = apiResponse.path("results");

        if (results.isArray() && results.size() > 0) {
            // Pega o primeiro resultado
            JsonNode firstResult = results.get(0);

            // Converte o JSON para um LivroDTO
            LivroDTO livroDTO = consumoApi.converterParaDTO(firstResult);

            // Cria um objeto Livro com os dados e o idioma selecionado
            Livro livro = new Livro(livroDTO.getTitulo(), livroDTO.getAutor(), idioma, livroDTO.getNumeroDownloads());

            // Salvar no banco de dados
            livroRepository.save(livro);

            System.out.println("Livro salvo no banco de dados: " + livroDTO.getTitulo() + " (Idioma: " + idioma + ")");
        } else {
            System.out.println("Nenhum livro encontrado com o título: " + titulo + " no idioma: " + idioma);
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
