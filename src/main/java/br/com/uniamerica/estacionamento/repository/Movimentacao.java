package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface Movimentacao extends JpaRepository<br.com.uniamerica.estacionamento.entity.Movimentacao, Long> {
    @Query("from Marca where nome Like :entrada")
    public List<Movimentacao> findByLike(@Param("entrada")final LocalDateTime entrada);


}
