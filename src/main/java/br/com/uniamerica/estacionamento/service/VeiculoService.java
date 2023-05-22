package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Veiculo atualizarVeiculo(Veiculo veiculo) {
        Assert.notNull(veiculo.getPlaca(), "O campo 'Placa' não pode ser nulo!");
        Assert.notNull(veiculo.getTipo(), "O campo 'Tipo' não pode ser nulo!");
        Assert.notNull(veiculo.getCor(), "O campo 'Cor' não pode ser nulo!");


        final List<Veiculo> veiculos = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculos.isEmpty(), "Veículo já cadastrado.");

        Assert.notNull(veiculo.getModelo(), "o campo 'Modelo' não pode ser nulo!");

        final Modelo modelo = this.modeloRepository.findById(veiculo.getModelo().getId()).orElse(null);
        Assert.notNull(modelo, "Modelo não localizado!");

        return this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public ResponseEntity<?> desativarVeiculo(Long id) {

        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        Assert.notNull(veiculoBanco, "Modelo não localizado!");

        if (!this.movimentacaoRepository.findByVeiculoId(id).isEmpty()) {
            veiculoBanco.setAtivo(false); //desativando o Veículo se ele possuir relação com Movimentação
            this.veiculoRepository.save(veiculoBanco);
            return ResponseEntity.ok("Veículo desativado.");
        } else {
            this.veiculoRepository.delete(veiculoBanco);
            return ResponseEntity.ok("Veículo deletado.");
        }
    }

    @Transactional
    public Veiculo cadastrar(Veiculo veiculo) {
        Assert.hasText(veiculo.getPlaca(), "O campo 'placa' não pode ser nulo!");
        Assert.notNull(veiculo.getTipo(), "O campo 'tipo' não pode ser nulo! ");
        Assert.notNull(veiculo.getModelo(), "O campo 'modelo' não pode ser nulo!");
        Assert.notNull(veiculo.getAno(), "O campo 'ano' não pode ser nulo!");
        Assert.notNull(veiculo.getCor(), "O campo 'cor' não pode ser nulo!");

        final List<Veiculo> veiculoBanco = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoBanco.isEmpty(), "Placa já cadastrada!");

       final Modelo modelos = this.modeloRepository.findById(veiculo.getModelo().getId()).orElse(null);
        Assert.notNull(modelos,"Modelo não localizado!");
        return this.veiculoRepository.save(veiculo);
    }
}