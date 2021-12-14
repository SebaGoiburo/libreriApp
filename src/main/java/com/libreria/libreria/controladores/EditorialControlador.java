
package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
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

@Controller
@RequestMapping("/editoriales")
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
public class EditorialControlador {
    
    @Autowired
    EditorialServicio editorialservicio;
    
    @GetMapping("/listarEditoriales")
    public String listarEditoriales(ModelMap modelo){
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "listarEditoriales.html";
    }
    
    @GetMapping("/modificarEditorial/{id}")
    public String formularioModEditorial(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialservicio.getOne(id));
        
        return "vistaU.html";
    }
    
    @PostMapping("/modificarEditorial/modificar")
        public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio{
          try {
            editorialservicio.modificarEditorial(id, nombre);
            String exito = "exito";
            modelo.put("exito",exito);
        } catch (Exception e) {
             modelo.put("error", e.getMessage());
             Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE,null, e);
            return "vistaU.html";
        }
        return "vistaU.html";
    }
    
    @GetMapping("/cargarEditorial")
    public String cargarEditorial(){
        return "cargarEditorial.html";
    }
    
    @PostMapping("cargarEditorial/cargar")
        public String cargar(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio{
            try {
            editorialservicio.cargarEditorial(nombre);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE,null, e);
            return "cargarEditorial.html";
        }   
        return "redirect:/editoriales";
    }
    
        
}
