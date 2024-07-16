package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(AutorDTO autorDTO) {
        this.name = autorDTO.name();
        this.birthYear = autorDTO.birth_year();
        try {
            this.deathYear = autorDTO.death_year();
        } catch (Exception e) {
            this.deathYear = null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    @Override
    public String toString() {
        String livrosString = livros.stream()
                .map(Livro::getTitle)
                .collect(Collectors.joining(", "));
        return "\nNome: " + name + '\n' +
                "Ano de nascimento: " + birthYear + '\n' +
                "Ano de falecimento: " + deathYear + '\n' +
                "Livros: " + livrosString + '\n';
    }
}