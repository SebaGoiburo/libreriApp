
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void cargarAutor(String nombre) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El autor debe tener un nombre o explicitarse como anónimo");
        } else {
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autor.setAlta(true);

            autorRepositorio.save(autor);
        }
    }
    
    public void modificarAutor(String id, String nombre){
        Autor a = autorRepositorio.getById(id);
        a.setNombre(nombre);
        autorRepositorio.save(a);
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores = autorRepositorio.findAll();
        return autores;
    } 
    
    public Autor getOne(String id){
        Autor a = autorRepositorio.getById(id);
        return a;
    }
    
}
