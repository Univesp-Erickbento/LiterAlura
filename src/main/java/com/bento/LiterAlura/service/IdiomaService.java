package com.bento.LiterAlura.service;

import com.bento.LiterAlura.service.model.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IdiomaService {

    private final ConsumoApi consumoApi;

    @Autowired
    public IdiomaService(ConsumoApi consumoApi) {
        this.consumoApi = consumoApi;
    }

    public void listarLivrosPorIdioma(Scanner scanner) {
        System.out.println("Insira o idioma para realizar a busca (es, en, fr, pt):");
        String idioma = scanner.nextLine();
        String jsonResponse = consumoApi.obterDados("https://gutendex.com/books", "languages=" + idioma);
        System.out.println("Livros no idioma " + idioma + ": " + jsonResponse);
    }
}
