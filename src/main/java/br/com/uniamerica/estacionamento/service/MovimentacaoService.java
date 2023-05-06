package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    public Movimentacao cadastrar(Movimentacao movimentacao){
        Assert.notNull(movimentacao.getCondutor(), "O campo 'condutor' não pode ser nulo!");
        Assert.notNull(movimentacao.getEntrada(), "O campo 'entrada' não pode ser nulo!");
        Assert.notNull(movimentacao.getVeiculo(), "O campo 'veiculo' não pode ser nulo!");

        final Condutor condutor = this.condutorRepository.findById(movimentacao.getCondutor().getId()).orElse(null);
        Assert.notNull(condutor, "Condutor não localizado!");
        final Veiculo veiculo = this.veiculoRepository.findById(movimentacao.getVeiculo().getId()).orElse(null);
        Assert.notNull(veiculo, "Veiculo não localizado!");

        return this.movimentacaoRepository.save(movimentacao);
    }
    public Movimentacao atualizarMovimentacao(Long id, Movimentacao movimentacao){

        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.notNull(movimentacaoBanco, "Movimentação não localizada!");
        Assert.isTrue(movimentacaoBanco.getId().equals(movimentacao.getId()), "Movimentação informada indefere com a cadastrada!");

        Assert.notNull(movimentacao.getCondutor(), "O campo 'condutor' não pode ser nulo!");
        Assert.notNull(movimentacao.getEntrada(), "O campo 'entrada' não pode ser nulo!");
        Assert.notNull(movimentacao.getVeiculo(), "O campo 'veiculo' não pode ser nulo!");


        final Condutor condutor = this.condutorRepository.findById(movimentacao.getCondutor().getId()).orElse(null);
        Assert.notNull(condutor, "Condutor não localizado!");
        final Veiculo veiculo = this.veiculoRepository.findById(movimentacao.getVeiculo().getId()).orElse(null);
        Assert.notNull(veiculo, "Veiculo não localizado!");

        return this.movimentacaoRepository.save(movimentacao);

    }
    @Transactional
    public ResponseEntity<?> desativar(Long id){

        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.notNull(movimentacao, "Movimentação não localizada!");

        movimentacao.setAtivo(false);
        return ResponseEntity.ok("Movimentação deletada.");
    }

}
