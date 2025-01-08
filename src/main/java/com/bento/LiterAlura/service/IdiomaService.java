package com.bento.LiterAlura.service;

import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.ConsumoApi;
import com.bento.LiterAlura.service.model.entities.Livro;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class IdiomaService {

    private final ConsumoApi consumoApi;
    private final LivroRepository livroRepository;

    @Autowired
    public IdiomaService(ConsumoApi consumoApi, LivroRepository livroRepository) {
        this.consumoApi = consumoApi;
        this.livroRepository = livroRepository;
    }

    public void listarLivrosPorIdioma(Scanner scanner) {
        System.out.println("Insira o idioma para realizar a busca (es, en, fr, pt):");
        String idioma = scanner.nextLine();

        // Verifica no banco de dados se já existe um livro nesse idioma
        Optional<Livro> livroExistente = livroRepository.findFirstByIdioma(idioma);
        if (livroExistente.isPresent()) {
            System.out.println("Livro já cadastrado no banco de dados: " + livroExistente.get().getTitulo());
            return;
        }

        // Caso não exista, realiza a busca na API externa
        JsonNode apiResponse = consumoApi.buscarNaApiExterna("https://gutendex.com/books", "languages=" + idioma);

        // Processa os resultados
        JsonNode results = apiResponse.path("results");

        if (results.isArray() && results.size() > 0) {
            System.out.println("Livros encontrados no idioma " + idioma + ". Salvando no banco de dados...");

            results.forEach(book -> {
                String title = book.path("title").asText();
                String author = book.path("authors").size() > 0 ? book.path("authors").get(0).path("name").asText() : "Autor desconhecido";
                Livro livro = new Livro(title, author, idioma, 0);
                livroRepository.save(livro);
                System.out.println("- Livro salvo: " + title);
            });
        } else {
            System.out.println("Nenhum livro encontrado no idioma " + idioma);
        }
    }
}
