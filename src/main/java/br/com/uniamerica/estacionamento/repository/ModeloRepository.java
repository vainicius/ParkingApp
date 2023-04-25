package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Modelo extends JpaRepository<Modelo, Long> {
    @Query("from Modelo where nome Like :nomeMarca")
    public List<Modelo> findByLike(@Param("nomeMarca")final String nomeMarca);



}
