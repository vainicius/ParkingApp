package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDateTime;
@MappedSuperclass

public class AbstractEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name ="dtCadastro",nullable = false)
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "DtAtualizacao")
    private LocalDateTime edicao;
    @Getter @Setter
    @Column(name="ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }
    @PreUpdate
    private void preUpdate(){
        this.edicao = LocalDateTime.now();
    }


}
