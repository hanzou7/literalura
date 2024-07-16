package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.LivroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String language;
    private Integer downloadCount;
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    private Autor autor;

    public Livro() {
    }

    public Livro(LivroDTO livroDTO) {
        this.id = livroDTO.id();
        this.title = livroDTO.title();
        this.language = livroDTO.languages().getFirst();
        this.downloadCount = livroDTO.download_count();
        this.autor = new Autor(livroDTO.authors().getFirst());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\nTitulo: " + title + '\n' +
                "Idioma: " + language + '\n' +
                "Downloads: " + downloadCount + '\n' +
                "Autor: " + autor.getName() + '\n' +
                "Id: " + id;
    }
}