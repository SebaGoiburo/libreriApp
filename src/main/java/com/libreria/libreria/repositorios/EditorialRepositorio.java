
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Editorial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarEditorial(@Param("nombre") String nombre);
    
    @Query("SELECT e.nombre FROM Editorial e")
    public List<String> listarEditoriales();
    
}
