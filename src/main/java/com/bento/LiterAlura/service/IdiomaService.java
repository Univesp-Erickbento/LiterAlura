package com.bento.LiterAlura.service;

import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.ConsumoApi;
import com.bento.LiterAlura.service.model.DTOs.LivroDTO;
import com.bento.LiterAlura.service.model.entities.Livro;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        // Busca na API externa
        JsonNode apiResponse = consumoApi.buscarNaApiExterna("https://gutendex.com/books", "languages=" + idioma);
        JsonNode results = apiResponse.path("results");

        if (results.isArray() && results.size() > 0) {
            System.out.println("Livros encontrados no idioma " + idioma + ". Salvando no banco de dados...");

            results.forEach(book -> {
                LivroDTO livroDTO = consumoApi.converterParaDTO(book);
                LivroDTO.AuthorDTO author = livroDTO.getAuthors().length > 0 ? livroDTO.getAuthors()[0] : null;

                // Dados do autor
                String nomeAutor = author != null ? author.getName() : "Autor desconhecido";
                LocalDate dataNascimento = author != null && author.getBirthYear() != null ? LocalDate.of(author.getBirthYear(), 1, 1) : null;
                LocalDate dataFalecimento = author != null && author.getDeathYear() != null ? LocalDate.of(author.getDeathYear(), 1, 1) : null;

                // Cria o objeto Livro
                Livro livro = new Livro(
                        livroDTO.getTitulo(),
                        nomeAutor,
                        idioma,
                        livroDTO.getNumeroDownloads(),
                        dataNascimento,
                        dataFalecimento
                );

                // Salva no banco
                livroRepository.save(livro);
                System.out.println("- Livro salvo: " + livro.getTitulo() + " (Autor: " + nomeAutor + ")");
            });
        } else {
            System.out.println("Nenhum livro encontrado no idioma " + idioma);
        }
    }
}
