package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.beans.Transient;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Modelo cadastrar(final Modelo modelo){
        Assert.notNull(modelo.getNomeModelo(), "O nome não pode estar vazio");
        Assert.notNull(modelo.getMarca(),"A marca não pode ser vazia");
        final Marca marca = this.marcaRepository.findById(modelo.getMarca().getId()).orElse(null);
        Assert.notNull(marca,"Marca não existe");

        return this.modeloRepository.save(modelo);
    }
    @Transactional
    public Modelo editar(Long id,Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        Assert.notNull(modeloBanco,"Modelo não existe!");
        Assert.isTrue(modeloBanco.getId().equals(modelo.getId()),"O modelo a ser editado indefere com o modelo cadastrado!.");
        Assert.notNull(modelo.getNomeModelo(),"O campo 'nome' não pode ser nulo!");
        Assert.notNull(modelo.getCadastro(),"O campo 'cadastro' não pode ser nulo!");
        Assert.notNull(modelo.getMarca(),"o campo 'marca' não pode ser nulo!");
        final Marca marca = this.marcaRepository.findById(modelo.getMarca().getId()).orElse(null);
        return this.modeloRepository.save(modelo);
    }
    @Transactional
    public ResponseEntity<?> desativar(Long id){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        Assert.notNull(modeloBanco, "Modelo não localizado!");

        if(!this.veiculoRepository.findByModeloId(id).isEmpty()){
            modeloBanco.setAtivo(false); //desativando o modelo caso ele esteja relacionado com um veículo.
            this.modeloRepository.save(modeloBanco);
            return ResponseEntity.ok("Modelo desativado.");
        }
        else{
            this.modeloRepository.delete(modeloBanco);
            return ResponseEntity.ok("Modelo deletado.");
        }
    }
}
