
package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private UsuarioServicio usuarioservicio;
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id){
        
        try {
            Usuario u = usuarioservicio.getById(id);
            if(u.getFoto()==null){
                throw new ErrorServicio("El usuario no tiene foto");
            }
            
            byte[] foto = u.getFoto().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE,null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
