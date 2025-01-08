package com.bento.LiterAlura.service;

import com.bento.LiterAlura.service.model.ConsumoApi;
import com.fasterxml.jackson.databind.JsonNode;
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

        // Busca os autores vivos na API externa
        JsonNode apiResponse = consumoApi.buscarNaApiExterna("https://gutendex.com/books", "author_year_start=" + ano);

        // Verifica se há resultados e exibe os dados
        JsonNode results = apiResponse.path("results");

        if (results.isArray() && results.size() > 0) {
            System.out.println("Autores vivos a partir de " + ano + ":");
            results.forEach(author -> {
                String name = author.path("name").asText();
                System.out.println("- " + name);
            });
        } else {
            System.out.println("Nenhum autor encontrado vivo a partir do ano " + ano);
        }
    }
}
