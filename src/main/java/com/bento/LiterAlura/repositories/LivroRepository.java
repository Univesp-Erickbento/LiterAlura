package com.bento.LiterAlura.repositories;

import com.bento.LiterAlura.service.model.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método para buscar livro pelo título
    Optional<Livro> findByTitulo(String titulo);



        @Query("SELECT l FROM Livro l WHERE l.idioma = ?1")
        Optional<Livro> findFirstByIdioma(String idioma);
    }

