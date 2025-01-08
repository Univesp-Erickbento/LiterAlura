package com.bento.LiterAlura.repositories;

import com.bento.LiterAlura.service.model.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método para buscar livro pelo título
    Optional<Livro> findByTitulo(String titulo);

    // Método para buscar o primeiro livro pelo idioma
    @Query("SELECT l FROM Livro l WHERE l.idioma = ?1")
    Optional<Livro> findFirstByIdioma(String idioma);

    // Método para buscar todos os livros pelo idioma
    List<Livro> findByIdioma(String idioma); // Adicionado para uso na PesquisaLivroService
}
