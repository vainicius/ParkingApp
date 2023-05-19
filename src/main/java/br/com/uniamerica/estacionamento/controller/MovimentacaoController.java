package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id) {
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null ? ResponseEntity.badRequest().body("Nenhuma movimentação encontrada") :
                ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

    @GetMapping("/lista/abertas")
    public ResponseEntity<?> findAllAtivos() {
        return ResponseEntity.ok(this.condutorRepository.findByAtivo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao) {
        try {
            final Movimentacao movimentacaoBanco = this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizarMovimentacao(
            @RequestParam("id") final Long id,
            @RequestBody Movimentacao movimentacao
    ) {
        try {
            final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

            if (movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacao.getId())) {
                throw new RuntimeException("Não foi possivel identificar a movimentacao informada!");
            }

            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Movimentacao atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> apagarMovimentacao(
            @RequestParam("id") final Long id
    ) {
        try {
            final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
            if (movimentacaoBanco == null) {
                throw new RuntimeException("Movimentação não encontrada");
            }
            if (!this.movimentacaoRepository.findById(id).isEmpty()) {
                movimentacaoBanco.setAtivo(false); //Altera par falso
                this.movimentacaoRepository.save(movimentacaoBanco);
                return ResponseEntity.ok("Alterado");
            } else {
                this.movimentacaoRepository.delete(movimentacaoBanco); //deleta o Banco
                return ResponseEntity.ok("Movimentacao apagada com sucesso!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


