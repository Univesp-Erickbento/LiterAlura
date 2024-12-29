package com.bento.LiterAlura.repositories;

import com.bento.LiterAlura.service.model.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método para buscar livro pelo título
    Optional<Livro> findByTitulo(String titulo);
}
