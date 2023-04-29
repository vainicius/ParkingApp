package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
//    @Query("from Veiculos where placa = :placa")
//    public List<Veiculo> findByPlaca(@Param("placa") final String placa);
@Query("from Veiculos where ativo = true")
public List<Veiculo>findByAtivos();
}
