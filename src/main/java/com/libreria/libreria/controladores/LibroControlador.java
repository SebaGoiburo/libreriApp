
package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/libros")
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
public class LibroControlador {
    
    @Autowired
    LibroServicio libroservicio;
    
    @Autowired
    AutorServicio autorservicio;
    
    @Autowired
    EditorialServicio editorialservicio;
    
    @GetMapping("/cargarLibro")
    public String cargarLibro(ModelMap modelo){
        List<Autor> autores = autorservicio.listarAutores();
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "cargarLibro.html";
    }
    
    @PostMapping("cargarLibro/cargar")
        public String cargar(ModelMap modelo, @RequestParam String titulo,@RequestParam String id_autor,@RequestParam String id_editorial, MultipartFile foto, @RequestParam Integer ejemplares, @RequestParam String isbn) throws ErrorServicio{
            try {
            libroservicio.cargarLibro(titulo, id_autor, id_editorial, foto, ejemplares, isbn);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("autor", id_autor);
            modelo.put("editorial", id_editorial);
            modelo.put("ejemplares", ejemplares);
            modelo.put("isbn", isbn);
            List<Autor> autores = autorservicio.listarAutores();
            List<Editorial> editoriales = editorialservicio.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE,null, e);
            return "redirect:/cargarLibro.html";
        }
            String exito = "Carga de libro exitosa"; 
            modelo.put("exito", exito);
        return "redirect:/libros.html";
    }
    
    
    @GetMapping("/modificarLibro/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo){
        modelo.put("libro", libroservicio.getOne(id));
        List<Autor> autores = autorservicio.listarAutores();
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales); 
    return "vistaU.html";
    }
    
    @PostMapping("/modificarLibro/modificar")
        public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam String titulo, @RequestParam String id_autor, @RequestParam String id_editorial, MultipartFile foto, @RequestParam Integer ejemplares, @RequestParam String isbn) throws ErrorServicio{
            try {
            libroservicio.modificarLibro(id, titulo, id_autor, id_editorial, foto, ejemplares, isbn);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("autor", id_autor);
            modelo.put("editorial", id_editorial);
            modelo.put("ejemplares", ejemplares);
            modelo.put("isbn", isbn);
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE,null, e);
            return "index.html";
        }   
        return "/libros.html";
    }
    
    @GetMapping("/listaLibros")
    public String listaLibros(ModelMap modelo){
        List<Libro> libros = libroservicio.listaLibros();
        modelo.addAttribute("libros", libros);
        
        return "listaLibros.html";        
    }
    
    
    
    
    
}
