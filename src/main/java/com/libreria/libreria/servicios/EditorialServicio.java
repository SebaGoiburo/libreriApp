
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void cargarEditorial(String nombre) throws ErrorServicio{
         if (nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("La editorial debe tener un nombre o explicitarse como autoeditado");
        } else {
             Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
       
            editorialRepositorio.save(editorial); 
        }
    }
    
    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
    
    public void modificarEditorial(String id, String nombre){
        Editorial e = editorialRepositorio.getById(id);
        e.setNombre(nombre);
        editorialRepositorio.save(e);
    }
    
    public Editorial getOne(String id){
        Editorial e = editorialRepositorio.getById(id);
        return e;
    }
    
}
