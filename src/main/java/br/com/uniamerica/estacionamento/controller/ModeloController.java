package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    /*
     * Injection
     * */
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloService modeloService;


    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null ? ResponseEntity.badRequest().body("Nenhum modelo encontrado") : ResponseEntity.ok(modelo);
    }
//----------------------------------- FIND ALL ------------------------------------------------
    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }
//-------------------------------------- CADASTRAR -----------------------------------------
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try{
            final Modelo modeloBanco = this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro feito com sucesso");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
//---------------------------------------- PUT -----------------------------------------
    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Modelo modelo
    ){
        try{
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

            if (modelo == null || !modeloBanco.getId().equals(modelo.getId())) {
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            final Modelo modeloAtualizado = this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro atualizado com sucesso");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //--------------------------------- FIND BY ATIVOS -----------------------
    @GetMapping("/lista/ativos")
    public ResponseEntity<?> findAllAtivos(){
        return ResponseEntity.ok(this.modeloRepository.findByAtivo());
    }
    //---------------------------- DELETE ------------------------------------
    @DeleteMapping
    public ResponseEntity<?> desativarModelo(
            @RequestParam("id") final Long id
    ){
        try{
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
            if(modeloBanco == null){
                throw new RuntimeException("Modelo não encontrado!");
            }
            if(!this.veiculoRepository.findByModeloId(id).isEmpty()){
                modeloBanco.setAtivo(false); //Altera par falso
                this.modeloRepository.save(modeloBanco); //Salva o novo status
                return ResponseEntity.ok("Modelo desativado com sucesso!");
            }else{
                this.modeloRepository.delete(modeloBanco); //deleta o Banco
                return ResponseEntity.ok("Modelo apagado com sucesso!");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}