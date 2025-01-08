package com.bento.LiterAlura.service.model;

import com.bento.LiterAlura.service.model.DTOs.LivroDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ConsumoApi {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Busca dados da API externa usando o título do livro.
     *
     * @param baseUrl URL base da API.
     * @param query Consulta a ser feita na API.
     * @return Objeto JsonNode contendo os resultados da API.
     */
    public JsonNode buscarNaApiExterna(String baseUrl, String query) {
        try {
            // Codifica a consulta
            String encodedQuery = URLEncoder.encode(query.trim(), StandardCharsets.UTF_8);
            String url = baseUrl + "/?" + encodedQuery;

            System.out.println("URL gerada para a API: " + url);

            // Cria a requisição HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Envia a requisição e obtém a resposta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Retorna a resposta como um objeto JsonNode
            return objectMapper.readTree(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao acessar a API: " + e.getMessage(), e);
        }
    }

    /**
     * Converte os dados recebidos da API em um objeto LivroDTO.
     *
     * @param jsonNode Nó do JSON contendo os dados do livro.
     * @return LivroDTO preenchido com os dados do JSON.
     */
    public LivroDTO converterParaDTO(JsonNode jsonNode) {
        try {
            return objectMapper.treeToValue(jsonNode, LivroDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON para LivroDTO: " + e.getMessage(), e);
        }
    }
}
