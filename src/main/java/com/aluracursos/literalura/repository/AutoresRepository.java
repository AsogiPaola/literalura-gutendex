package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.datosBusqueda.DatosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoresRepository extends JpaRepository<DatosAutor, Long> {
    @Query("SELECT a FROM DatosAutor a WHERE :anio between a.birth_year AND a.death_year")
    List<DatosAutor> findForYear(int anio);

}
