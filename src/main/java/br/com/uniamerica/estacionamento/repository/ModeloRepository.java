package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    @Query("from Modelo where marca.id = :id")
    public List<Modelo> findByMarcaId(@Param("id") final Long id);
    @Query("from Modelo where ativo = true")
    public List<Modelo> findByAtivo();

    @Query ("from Modelo where nomeModelo = :nomeModelo")
    public List<Marca>findByNome(@Param("nomeModelo") final String nomeModelo);


}
