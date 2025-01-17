package com.aluracursos.literalura.datosBusqueda;

import com.aluracursos.literalura.main.CadenaTexto;
import com.aluracursos.literalura.model.Autores;
import jakarta.persistence.*;

@Entity
@Table(name = "Autores")
public class DatosAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birth_year;
    private Integer death_year;

    @OneToOne
    @JoinTable(name = "Libro",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))

    private DatosLibro libros;

    public DatosAutor(){}

    public DatosAutor(Autores autor){
        this.name = CadenaTexto.longitudCadena(autor.name(), 300);
        if (autor.birthYear()== null){
            this.birth_year = 1980;
        }
        else {
            this.birth_year = autor.birthYear();
        }

        if (autor.deathYear() == null){
            this.death_year = 3022;
        }
        else{
            this.death_year = autor.deathYear();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    //---------------------------------------
    public DatosLibro getLibros() {
        return libros;
    }

    public void setLibros(DatosLibro libros) {
        this.libros = libros;
    }

    @Override
    public String toString(){
        return "DatosAutor [id=" + id + ", name=" + name + ", birth_year=" + birth_year
                + ", death_year=" + death_year + ", libro=" + "]";
    }


}
