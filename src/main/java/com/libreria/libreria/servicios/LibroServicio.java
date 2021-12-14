
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void cargarLibro(String titulo, String id_autor, String id_editorial, MultipartFile foto, Integer ejemplares, String isbn) throws ErrorServicio{
        try {
            validarCarga(titulo, id_autor, id_editorial, ejemplares, isbn);
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setFoto(foto);
            libro.setAutor(autorRepositorio.getById(id_autor));
            libro.setEditorial(editorialRepositorio.getById(id_editorial));
            libroRepositorio.save(libro);
        } catch (ErrorServicio e) {
            throw new ErrorServicio("Ha ocurrido un error durate la carga");
        }
    }
    
    @Transactional
    public void modificarLibro(String id, String titulo, String id_autor, String id_editorial, MultipartFile foto, Integer ejemplares, String isnb) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setTitulo(titulo);
            l.setEjemplares(ejemplares);
            Autor a = autorRepositorio.getById(id_autor);
            l.setAutor(a);
            Editorial e = editorialRepositorio.getById(id_editorial);
            l.setEditorial(e);
            l.setFoto(foto);
            l.setIsbn(isnb);
            libroRepositorio.save(l);
        } else{
            throw new ErrorServicio("No se halló el libro buscado");
        }
    }
    
    @Transactional
    public void eliminarLibro(Libro l) throws ErrorServicio{
        Optional<Libro> resp = libroRepositorio.findById(l.getId());
        if(resp.isPresent()){
            Libro libro = resp.get();
            l.setBaja(true);
            l.setAlta(false);
            libroRepositorio.save(libro);
        } else{
            throw new ErrorServicio("No se halló el libro buscado");
        }
    }
    
    public void validarCarga(String titulo, String id_autor, String id_editorial, Integer ejemplares, String isbn) throws ErrorServicio{
        if (titulo == null || titulo.isEmpty()){
            throw new ErrorServicio("El libro debe tener un título");
        }
        if (id_autor == null|| id_autor.isEmpty()){
            throw new ErrorServicio("El libro debe tener un autor. Si es anónimo debe indicarse explícitamente");
        }
        if (id_editorial == null|| id_editorial.isEmpty()){
            throw new ErrorServicio("El libro debe tener una editorial. Si es autoeditado, debe indicarse explícitamente");
        }
        if (ejemplares == null){
            ejemplares = 1;
        }
        if (isbn == null){
            throw new ErrorServicio("El libro debe tener un ISBN");
        }
    }
    
    public List<Libro> listaLibros(){
        List<Libro> libros = libroRepositorio.findAll();
        return libros;
    }
    
    public Libro getOne(String id){
        Libro libro = new Libro();
        libro = libroRepositorio.getById(id);
        return libro;
    }
    
}
