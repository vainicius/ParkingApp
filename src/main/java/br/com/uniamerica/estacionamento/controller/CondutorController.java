package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

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
    public ResponseEntity<?> cadastrar (@RequestBody final Condutor condutor){
        try{
            this.condutorRepository.save(condutor);
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
                throw new RuntimeException("Não foi possivel identifica o condutor informado!");
            }

            this.condutorRepository.save(condutor);
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
                return ResponseEntity.ok("Marca desativada com sucesso!");
            }else{
                this.condutorRepository.delete(condutorBanco); //deleta o Banco
                return ResponseEntity.ok("Marca apagada com sucesso!");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
