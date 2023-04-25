package br.com.uniamerica.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<ModeloRepository, Long> {
    @Query("from Modelo where nome Like :nomeMarca")
    public List<ModeloRepository> findByLike(@Param("nomeMarca")final String nomeMarca);



}
