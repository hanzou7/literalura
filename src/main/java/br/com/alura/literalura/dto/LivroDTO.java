package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(Long id,
                       String title,
                       List<AutorDTO> authors,
                       List<String> languages,
                       Integer download_count) {
}
