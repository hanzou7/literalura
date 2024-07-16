package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.name = :autor")
    Autor findAutorByName(String autor);

    @Query("SELECT a FROM Livro b JOIN b.autor a WHERE a.birthYear <= :year and a.deathYear >= :year")
    List<Autor> searchAutorYear(Integer year);
}
