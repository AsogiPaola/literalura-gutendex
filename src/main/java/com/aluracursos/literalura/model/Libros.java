package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libros(
        @JsonAlias("id") String id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<Autores> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer download_count
) {

}
