package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ConfiguracaoService {
    @Autowired
    ConfiguracaoRepository configuracaoRepository;

    @Transactional
    public Configuracao cadastrar(final Configuracao configuracao){
        Assert.notNull(configuracao.getInicioExpediente(),"o campo 'InicioExpediente' deve ser preenchido!");
        Assert.notNull(configuracao.getValorHora(),"O campo 'ValorHora' deve ser preenchido!");
        Assert.notNull(configuracao.getFimExpediente(),"O campo 'FimExpediente' deve ser preenchido!");

        return this.configuracaoRepository.save(configuracao);
    }
    @Transactional
    public Configuracao atualizarConfig(Long id, Configuracao configuracao){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);
        Assert.notNull(configuracaoBanco,"Configuração não localizada!");
        Assert.isTrue(configuracao.getId().equals(configuracaoBanco.getId()),"Configuração informada indefere com a cadastrada!");

        Assert.notNull(configuracao.getInicioExpediente(),"o campo 'InicioExpediente' deve ser preenchido!");
        Assert.notNull(configuracao.getValorHora(),"O campo 'ValorHora' deve ser preenchido!");
        Assert.notNull(configuracao.getFimExpediente(),"O campo 'FimExpediente' deve ser preenchido!");

        return this.configuracaoRepository.save(configuracao);

    }
}
