package com.bento.LiterAlura.service;

import com.bento.LiterAlura.service.model.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AutorService {

    private final ConsumoApi consumoApi;

    @Autowired
    public AutorService(ConsumoApi consumoApi) {
        this.consumoApi = consumoApi;
    }

    public void listarAutoresRegistrados() {
        System.out.println("Não implementado: Listar autores registrados.");
    }

    public void listarAutoresVivos(Scanner scanner) {
        System.out.println("Insira o ano que deseja pesquisar:");
        int ano = scanner.nextInt();
        scanner.nextLine();
        String jsonResponse = consumoApi.obterDados("https://gutendex.com/books", "author_year_start=" + ano);
        System.out.println("Autores vivos a partir de " + ano + ": " + jsonResponse);
    }
}
