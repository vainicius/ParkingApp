package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id) {
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);
        return configuracao == null ? ResponseEntity.badRequest().body("Sem configurações encontradas") : ResponseEntity.ok(configuracao);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizarConfig(
            @RequestParam("id") final Long id,
            @RequestBody Configuracao configuracao
    ){
        try {
            final Configuracao configBanco = this.configuracaoRepository.findById(id).orElse(null);

            if (configBanco == null || !configBanco.getId().equals(configuracao.getId())) {
                throw new RuntimeException("Configuração não encontrada");
            }

            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Configuração atualizada com sucesso");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
