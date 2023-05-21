package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {
    @Autowired
    CondutorRepository condutorRepository;
    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Condutor cadastrar(final Condutor condutor){
        Assert.hasText(condutor.getNomeCondutor(),"Campo 'nomeCondutor' não localizado!");
        Assert.hasText(condutor.getCpf(),"Campo 'cpf' não localizado!");
        final List<Condutor>Condutores = this.condutorRepository.findByCpf(condutor.getCpf());
        Assert.isTrue(Condutores.isEmpty(),"Cpf já cadastrado.");
        final List<Condutor>CondutoresTelefone = this.condutorRepository.findByTelefone(condutor.getTelefone());
        Assert.isTrue(CondutoresTelefone.isEmpty(),"Telefone já cadastrado.");
        Assert.isTrue(condutor.getNomeCondutor().length() <=50,String.format("O nome do condutor possui %s caracteres, o limite é 50!", condutor.getNomeCondutor().length()));

        return this.condutorRepository.save(condutor);
    }

    @Transactional
    public Condutor editar(Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        Assert.notNull(condutorBanco,"Condutor não encontrado.");
        Assert.isTrue(condutorBanco.getId().equals(condutor.getId()),"O condutor a ser editado indefere com o condutor cadastrado!");

        Assert.notNull(condutor.getCadastro(), "O campo 'cadastro' não pode ser nulo!");
        Assert.hasText(condutor.getNomeCondutor(), "O campo 'nome' não pode ser nulo!");
        Assert.isTrue(  condutor.getNomeCondutor().length() <=50,"O nome do condutor possui mais de 50 caracteres!");
        Assert.notNull(condutor.getCpf(), "O campo 'cpf' não pode ser nulo!");
        final List<Condutor>Condutores = this.condutorRepository.findByCpf(condutor.getCpf());
        final List<Condutor>CondutoresTelefone = this.condutorRepository.findByTelefone(condutor.getTelefone());
        Assert.isTrue(CondutoresTelefone.isEmpty(),"Telefone já cadastrado.");
        Assert.isTrue(Condutores.isEmpty(),"Cpf já cadastrado.");
        return this.condutorRepository.save(condutor);
    }

    @Transactional
    public ResponseEntity<?>desativarCondutor(long id){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        Assert.notNull(condutorBanco, "Condutor não localizado!");

        if(!this.movimentacaoRepository.findByVeiculoId(id).isEmpty()){
            condutorBanco.setAtivo(false); //Desativando o condutor caso ele esteja vinculado a uma movimentação
            this.condutorRepository.save(condutorBanco);
            return ResponseEntity.ok("Condutor desativado, pois existe histórico de movimentação!.");
        }else{
            this.condutorRepository.delete(condutorBanco);
            return ResponseEntity.ok("Condutor deletado com sucesso.");
        }
    }



}
