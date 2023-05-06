package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long>
{
//    @Query("from Condutor where nomeCondutor like :nomeCondutor")
//    public List<Condutor> findByLike(@Param("nomeCondutor")final String nomeCondutor);
    @Query("from Condutor where ativo = true")
    public List<Condutor> findByAtivo();
    @Query("from Condutor where cpf = :cpf")
    public List<Condutor> findByCpf(@Param("cpf") final String cpf);
}
