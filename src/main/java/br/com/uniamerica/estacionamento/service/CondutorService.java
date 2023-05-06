package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CondutorService {
    @Autowired
    CondutorRepository condutorRepository;

    @Transactional
    public Condutor cadastrar(final Condutor condutor){
        Assert.notNull(condutor.getNomeCondutor(),"O campo 'nome' não pode ser nulo!");
        Assert.notNull(condutor.getCpf(),"O campo 'cpf' não pode ser nulo!");
        final List<Condutor>Condutores = this.condutorRepository.findByCpf(condutor.getCpf());
        Assert.isTrue(Condutores.isEmpty(),"Cpf já cadastrado.");

        return this.condutorRepository.save(condutor);
    }




}
