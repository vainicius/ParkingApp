package br.com.uniamerica.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.uniamerica.estacionamento.entity.Marca;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long>  {

    @Query("from Marca where nome Like :nome")
    public List<Marca> findByLike(@Param("nome")final String nome);

}
