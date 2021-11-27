
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarLibroxTit(@Param("titulo")String titulo);
    
//    @Query("SELECT l FROM Libro WHERE l.isbn = :isbn")
//    public Libro buscarLibroxIsbn(@Param("isbn")Integer isbn);
    
//    @Query("SELECT l FROM Libro l WHERE l.Autor.nombre = :nombre")
//    public List<Libro> buscarLibrosxAutor(@Param("nombre")String nombre);
    
}
