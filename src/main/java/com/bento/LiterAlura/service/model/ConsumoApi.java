package com.bento.LiterAlura.service.model;

import com.bento.LiterAlura.service.model.DTOs.LivroDTO;
import com.bento.LiterAlura.repositories.LivroRepository;
import com.bento.LiterAlura.service.model.entities.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ConsumoApi {

    @Autowired
    private LivroRepository livroRepository;

    public String obterDados(String baseUrl, String query) {
        HttpClient client = HttpClient.newHttpClient();

        // Substituir espaços por "%20" e codificar a query inteira
        String formattedQuery = query.replace(" ", "%20");
        String encodedQuery = URLEncoder.encode(formattedQuery, StandardCharsets.UTF_8);

        // Montar a URL final no formato correto
        String url = baseUrl + "/?" + encodedQuery;

        // Exibir a URL para depuração
        System.out.println("URL completa: " + url);

        // Buscar livro no banco de dados
        Optional<Livro> livroOptional = livroRepository.findByTitulo(query);

        if (livroOptional.isPresent()) {
            // Livro encontrado no banco, converter para DTO
            Livro livro = livroOptional.get();
            LivroDTO livroDTO = converterParaDTO(livro);
            // Retornar informações do livro ou fazer outro processamento
            return "Livro encontrado no banco de dados: " + livroDTO.getTitulo();
        } else {
            // Livro não encontrado, buscar na API externa
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response;

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Erro ao acessar a API: " + e.getMessage(), e);
            }

            // Exibir a resposta para depuração
            System.out.println("Resposta da API: " + response.body());

            return response.body();
        }
    }

    private LivroDTO converterParaDTO(Livro livro) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo(livro.getTitulo());
        livroDTO.setAutor(livro.getAutor());
        livroDTO.setIdioma(livro.getIdioma());
        livroDTO.setNumeroDownloads(livro.getNumeroDownloads());
        return livroDTO;
    }
}
