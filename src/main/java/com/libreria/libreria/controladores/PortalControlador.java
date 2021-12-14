
package com.libreria.libreria.controladores;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import com.libreria.libreria.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private LibroServicio libroservicio;
    
    @Autowired
    private AutorServicio autorservicio;
    
    @Autowired
    private EditorialServicio editorialservicio;
    
    @Autowired
    private UsuarioServicio usuarioservicio;
    
   
    @GetMapping("/")
    public String index(){
    return "index.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio(){
//        Usuario u = new Usuario();
//        u = usuarioservicio.getOne(user.getUsername());
//        modelo.put("nombre", u.getNombre());
//        modelo.put("apellido", u.getApellido());
    return "vistaU.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo){
        if(error!= null){
            modelo.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null){
            modelo.put("logout", "Ha cerrado sesión correctamente");
        }
        return "login.html";
    }
    
    @GetMapping("/registro")
    public String registro(){
    return "registro.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, MultipartFile foto, @RequestParam String correo, @RequestParam String clave, @RequestParam String confirmarClave) throws ErrorServicio{
        try {
            usuarioservicio.registrarUsuario(nombre, apellido, foto, correo, clave, confirmarClave);
            String exito = "Usuario registrado con éxito!";
            modelo.put("exito", exito);
            return "redirect:/login";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("correo", correo);
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE,null, e);
            return "redirect:/registro";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/libros")
    public String libros(ModelMap modelo){
        List<Libro> libros = libroservicio.listaLibros();
        modelo.addAttribute("libros", libros);
    return "libros.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editoriales")
    public String editoriales(ModelMap modelo){
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
    return "editoriales.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/autores")
    public String autores(ModelMap modelo){
        List<Autor> autores = autorservicio.listarAutores();
        modelo.addAttribute("autores", autores);
        
    return "autores.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/perfil")
    public String perfil(){
        
    return "perfil.html";
    }
    
    
}
