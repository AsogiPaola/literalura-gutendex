package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.datosBusqueda.DatosLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<DatosLibro, Long> {
    @Query("SELECT l FROM DatosLibro l WHERE l.languages >= :idioma")
    List<DatosLibro> findForLanguages(String idioma);
    Optional<DatosLibro> findByTitle(String title);}
