package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
//    @Query("from Configuracao where valorHora = :valorHora")
//    public List<Configuracao> findByLike(@Param("valorHora")final BigDecimal valorHora);

    @Query("from Configuracao where id = 1 order by id desc limit 1")
    public Configuracao findByConfiguracao();

}
