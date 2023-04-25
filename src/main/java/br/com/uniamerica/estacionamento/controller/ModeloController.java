package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")

public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;
    @GetMapping("{id}")
    public ResponseEntity<Modelo> findByIdPatch(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }
    @GetMapping
    public ResponseEntity<Modelo> findById(@RequestParam("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }

//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//
    /*
    * http://localhost:8080/api/modelo?id=1
    * http://localhost:8080/api/modelo/1
    *
    *
     */

}
