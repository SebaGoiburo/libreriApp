
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.FotoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoLibroRepositorio extends JpaRepository<FotoLibro, String>{

    public FotoLibro save(FotoLibro fot);
    
    
}
