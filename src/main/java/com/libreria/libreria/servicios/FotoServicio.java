
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.repositorios.FotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotorepositorio;
    
    public Foto guardar(MultipartFile foto) throws ErrorServicio{
        if(foto!=null){
            try {
                Foto fot = new Foto();
                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotorepositorio.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }  
        }
            return null;
    } 
    
    public Foto actualizar(String id, MultipartFile foto) throws ErrorServicio{
        if(foto!=null){
            try {
                Foto fot = new Foto();
                
                if (id != null){
                    Optional <Foto> respuesta = fotorepositorio.findById(id);
                    if(respuesta.isPresent()){
                        fot = respuesta.get();
                    }
                }
                
                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotorepositorio.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }  
        }
            return null;
    }
    
}


