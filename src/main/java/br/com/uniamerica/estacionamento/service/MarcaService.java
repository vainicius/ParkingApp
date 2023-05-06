package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MarcaService {
    @Autowired
    MarcaRepository marcaRepository;

    public Marca cadastrar(final Marca marca){
        Assert.notNull(marca.getNomeMarca(), "O campo 'nome' não pode ser nulo!");
        final List<Marca> marcas = this.marcaRepository.findByNome(marca.getNomeMarca());
        Assert.isTrue(marcas.isEmpty(),"Marca já cadastrada!");

        return this.marcaRepository.save(marca);
    }
    public Marca atualizarMarca(Long id, Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        Assert.notNull(marcaBanco, "Marca não encontrada!");
        Assert.isTrue(marcaBanco.getId().equals(marca.getId()), "A marca informada indefere com a marca cadastrada!");

        final List<Marca> marcas = this.marcaRepository.findByNome(marca.getNomeMarca());
        Assert.isTrue(marcas.isEmpty(),"Marca já cadastrada");

        Assert.notNull(marca.getNomeMarca(),"O campo 'nome' não pode ser nulo!");
        Assert.notNull(marca.getCadastro(),"O campo 'cadastro' não pode ser vazio!");

        return this.marcaRepository.save(marca);

    }

}
