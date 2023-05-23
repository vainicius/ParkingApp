package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<br.com.uniamerica.estacionamento.entity.Movimentacao, Long> {
//    @Query("from Marca where entrada = :entrada")
//    public List<MovimentacaoRepository> findByLike(@Param("entrada")final LocalDateTime entrada);
    @Query("from Movimentacao where condutor.id = :id")
    public List<Movimentacao> findByCondutorId(@Param("id") final Long id);
    @Query("from Movimentacao where ativo = true")
    public List<Movimentacao> findByAtivos();
    @Query("from Movimentacao where veiculo.id = :id")
    public List<Movimentacao> findByVeiculoId(@Param("id") final Long id);


}
