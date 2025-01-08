package com.bento.LiterAlura.service;

import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.entities.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class PesquisaLivroService {

    private final LivroRepository livroRepository;

    @Autowired
    public PesquisaLivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void pesquisarLivrosPorIdioma(Scanner scanner) {
        System.out.println("Insira o idioma para realizar a pesquisa (es, en, fr, pt):");
        String idioma = scanner.nextLine();

        // Busca os livros pelo idioma no banco de dados
        List<Livro> livros = livroRepository.findByIdioma(idioma);

        // Exibe os resultados
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("Livros encontrados no idioma " + idioma + ":");
            livros.forEach(livro -> System.out.println("Título: " + livro.getTitulo() +
                    ", Autor: " + livro.getAutor() +
                    ", Número de Downloads: " + livro.getNumeroDownloads()));
        }
    }
}
