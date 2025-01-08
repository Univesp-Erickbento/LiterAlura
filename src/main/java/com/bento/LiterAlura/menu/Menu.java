package com.bento.LiterAlura.menu;

import com.bento.LiterAlura.service.AutorService;
import com.bento.LiterAlura.service.IdiomaService;
import com.bento.LiterAlura.service.LivroService;
import com.bento.LiterAlura.service.PesquisaLivroService; // Importação da nova classe
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    private final LivroService livroService;
    private final AutorService autorService;
    private final IdiomaService idiomaService;
    private final PesquisaLivroService pesquisaLivroService; // Adicionado

    @Autowired
    public Menu(LivroService livroService, AutorService autorService, IdiomaService idiomaService, PesquisaLivroService pesquisaLivroService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.idiomaService = idiomaService;
        this.pesquisaLivroService = pesquisaLivroService; // Inicialização
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nEscolha o número de sua opção:");
            System.out.println("1- Buscar livro pelo título");
            System.out.println("2- Listar livros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos em determinado ano");
            System.out.println("5- Listar livros em um determinado idioma"); // Atualização na descrição
            System.out.println("0- Sair");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            processarOpcao(opcao, scanner);
        } while (opcao != 0);

        scanner.close();
    }

    private void processarOpcao(int opcao, Scanner scanner) {
        switch (opcao) {
            case 1 -> livroService.buscarLivroPorTitulo(scanner);
            case 2 -> livroService.listarLivrosRegistrados();
            case 3 -> autorService.listarAutoresRegistrados();
            case 4 -> autorService.listarAutoresVivos(scanner);
            case 5 -> pesquisaLivroService.pesquisarLivrosPorIdioma(scanner); // Chamada da nova classe
            case 0 -> System.out.println("Saindo da aplicação...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
