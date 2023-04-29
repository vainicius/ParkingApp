package br.com.uniamerica.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.uniamerica.estacionamento.entity.Marca;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long>  {
//    @Query("from Marca where nomeMarca like :nomeMarca")
//    public List<Marca> findByLike(@Param("nomeMarca")final String nomeMarca);
    @Query("from Marca where ativo = true")
    public List<Marca> findByAtivo();
}
