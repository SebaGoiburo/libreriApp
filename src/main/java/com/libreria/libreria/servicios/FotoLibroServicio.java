
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.FotoLibro;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.repositorios.FotoLibroRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoLibroServicio {

    @Autowired
    private FotoLibroRepositorio fotolibrorepositorio;
    
    public FotoLibro guardar(MultipartFile foto) throws ErrorServicio{
        if(foto!=null){
            try {
                FotoLibro fot = new FotoLibro();
                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotolibrorepositorio.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }  
        }
            return null;
    } 
    
    public FotoLibro actualizar(String id, MultipartFile foto) throws ErrorServicio{
        if(foto!=null){
            try {
                FotoLibro fot = new FotoLibro();
                
                if (id != null){
                    Optional<FotoLibro> respuesta = fotolibrorepositorio.findById(id);
                    if(respuesta.isPresent()){
                        fot = respuesta.get();
                    }
                }
               
                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotolibrorepositorio.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }  
        }
            return null;
    }
    
}


