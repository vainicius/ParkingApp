package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Veiculo atualizarVeiculo(Veiculo veiculo){
        Assert.notNull(veiculo.getPlaca(), "O campo 'Placa' não pode ser nulo!");
        Assert.notNull(veiculo.getTipo(), "O campo 'Tipo' não pode ser nulo!");
        Assert.notNull(veiculo.getCor(), "O campo 'Cor' não pode ser nulo!");


        final List<Veiculo> veiculos = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculos.isEmpty(),"Veículo já cadastrado.");

        Assert.notNull(veiculo.getModelo(), "o campo 'Modelo' não pode ser nulo!");

        final Modelo modelo = this.modeloRepository.findById(veiculo.getModelo().getId()).orElse(null);
        Assert.notNull(modelo, "Modelo não localizado!");

        return this.veiculoRepository.save(veiculo);
    }


}
