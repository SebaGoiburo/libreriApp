
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.errores.ErrorServicio;
import com.libreria.libreria.servicios.FotoServicio;
import com.libreria.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuariorepositorio;
    
    @Autowired
    private FotoServicio fotoservicio;
    
    @Transactional
    public void registrarUsuario(String nombre, String apellido, MultipartFile foto, String correo, String clave, String confirmarClave) throws ErrorServicio{
        validarUsuario(nombre, apellido, correo, clave, confirmarClave);
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
     
        Foto fot = new Foto(); 
        fot = fotoservicio.guardar((MultipartFile) foto);
        u.setFoto(fot);
        
        u.setCorreo(correo);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        u.setClave(encriptada);
        u.setAlta(true);
        usuariorepositorio.save(u);
        
    }
    
    public void validarUsuario(String nombre, String apellido, String correo, String clave, String confirmarClave) throws ErrorServicio{
        try {
            if(nombre==null || nombre.isEmpty()){
                throw new ErrorServicio("El usuario debe tener un nombre");
            }
            if(apellido==null || apellido.isEmpty()){
                throw new ErrorServicio("El usuario debe tener un apellido");
            }
            if(correo==null || correo.isEmpty()){
                throw new ErrorServicio("El usuario debe tener un correo");
            }
            Usuario u = usuariorepositorio.buscarUsuario(correo);
            if(u != null){
                throw new ErrorServicio("El correo declarado ya existe");
            }
            validarClave(clave, confirmarClave);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void validarClave(String clave, String confirmarClave) throws ErrorServicio{
        if(clave == null || clave.isEmpty()){
            throw new ErrorServicio("El usuario debe tener una clave");
        }
        if(clave.length()<8){
            throw new ErrorServicio("La clave debe tener más de ocho carácteres");
        }
        if(!clave.equals(confirmarClave)){
            throw new ErrorServicio("Las claves declaradas no coinciden");
        }
    }
    
    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, MultipartFile foto, String correo, String clave) throws ErrorServicio{
        Optional<Usuario> respuesta = usuariorepositorio.findById(id);
        if (respuesta.isPresent()){
            Usuario u = respuesta.get();
            u.setNombre(nombre);
            u.setApellido(apellido);
            
            String idFoto = null;
            if (u.getId()!=null){
                idFoto = u.getFoto().getId();
            }
            Foto fot= fotoservicio.actualizar(id, foto);
            
            u.setFoto(fot);
            u.setCorreo(correo);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            u.setClave(encriptada);
            usuariorepositorio.save(u);
        } else{
            throw new ErrorServicio("No se halló el usuario buscado");
        }
    }
    
    @Transactional
    public void eliminarLibro(Usuario u) throws ErrorServicio{
        Optional<Usuario> resp = usuariorepositorio.findById(u.getId());
        if(resp.isPresent()){
            Usuario us = resp.get();
            us.setAlta(false);
            usuariorepositorio.save(us);
        } else{
            throw new ErrorServicio("No se halló el usuario buscado");
        }
    }
 
    public void login(String correo, String clave){
        
        
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuariorepositorio.buscarUsuario(correo);
        if(usuario !=null){
            
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getCorreo(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        } 
    }
    
    public Usuario getById(String id){
        Usuario u = new Usuario();
        u= usuariorepositorio.getById(id);
        return u;
    }
    
    public Usuario getOne(String correo){
        Usuario u = new Usuario();
        u = usuariorepositorio.buscarUsuario(correo);
        return u;
    }
    
}
