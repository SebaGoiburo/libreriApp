
package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
public class AutorControlador {
  
    @Autowired
    AutorServicio autorservicio;
    
    
//    @GetMapping("/listarAutores")
//    public String listarAutores(ModelMap modelo){
//        List<Autor> autores = autorservicio.listarAutores();
//        modelo.addAttribute("autores", autores);
//        return "listarAutores.html";
//    }
    
    @GetMapping("/modificarAutor/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorservicio.getOne(id));
        
        return "vistaU.html";
    }
    
    @PostMapping("/modificarAutor/modificar")
        public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio{
          try {
            autorservicio.modificarAutor(id, nombre);
            String exito = "exito";
            modelo.put("exito",exito);
        } catch (Exception e) {
             modelo.put("error", e.getMessage());
             Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE,null, e);
            return "vistaU.html";
        }
        return "vistaU.html";
    }
    
    @GetMapping("/cargarAutor")
    public String cargarAutor(){
        
        return "cargarAutor.html";
    }
    
    @PostMapping("cargarAutor/cargar")
        public String cargar(ModelMap modelo, @RequestParam String nombre, RedirectAttributes rda) throws ErrorServicio{
            try {
            autorservicio.cargarAutor(nombre);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE,null, e);
            return "cargarAutor.html";
        }
        
        return "redirect:/autores";
    }
    
}
