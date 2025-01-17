package com.aluracursos.literalura.datosBusqueda;


import com.aluracursos.literalura.main.CadenaTexto;
import com.aluracursos.literalura.model.Autores;
import com.aluracursos.literalura.model.Libros;
import jakarta.persistence.*;

@Entity
@Table(name = "Libros")
public class DatosLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;

    private String languages;
    private Integer download_count;
    @OneToOne(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DatosAutor autor;

    public DatosLibro(){}

    public DatosLibro(Libros libro){
        this.title = CadenaTexto.longitudCadena(libro.title(), 200);
        this.download_count = libro.download_count();
        if (!libro.languages().isEmpty())
            this.languages = libro.languages().get(0);
        if (!libro.authors().isEmpty()) {
            for (Autores autor : libro.authors()) {
                this.autor = new DatosAutor(autor);
                break;
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public DatosAutor getAutor() {
        return autor;
    }

    public void setAutor(DatosAutor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "DatosLibro [id=" + id + ", title=" + title + ", languages=" + languages + ", download_count=" + download_count
                + ", autores=" + autor + "]";
    }




}
