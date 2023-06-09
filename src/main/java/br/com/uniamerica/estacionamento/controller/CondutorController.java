package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/condutor")

public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private CondutorService condutorService;
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null ? ResponseEntity.badRequest().body("Nenhum condutor encontrado") : ResponseEntity.ok(condutor);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }
    @GetMapping("/lista/ativos")
    public ResponseEntity<?> findAllAtivos(){
        return ResponseEntity.ok(this.condutorRepository.findByAtivo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody @Validated final Condutor condutor){
        try{

            final Condutor condutorBanco = this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> atualizarCondutor(
            @RequestParam("id") final Long id,
            @RequestBody Condutor condutor
    ){
        try {
            final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

            if (condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())) {
                throw new RuntimeException("Não foi possivel identificar o condutor informado!");
            }

            final Condutor condutorAtualizado = this.condutorService.editar(condutor);

            return ResponseEntity.ok("Condutor atualizado com sucesso");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> desativarCondutor(
            @RequestParam("id") final Long id
    ){
        try{
            final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
            if(condutorBanco == null){
                throw new RuntimeException("Condutor não encontrado!");
            }
            if(!this.movimentacaoRepository.findByCondutorId(id).isEmpty()){
                condutorBanco.setAtivo(false); //Altera par falso
                this.condutorRepository.save(condutorBanco); //Salva o novo status
                return ResponseEntity.ok("Condutor desativado, pois existe histórico de movimentação!");
            }else{
                this.condutorRepository.delete(condutorBanco); //deleta o Banco
                return ResponseEntity.ok("Condutor apagado com sucesso!");
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
