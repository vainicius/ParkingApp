package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Table(name="veiculo_table",schema = "public")
@Entity
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @Column(name="placa", nullable = false, length = 15)
    private String placa;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="",nullable = false)
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name="cor",nullable = false)
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name="tipo", nullable = false, length = 6)
    private Tipo tipo;
    @Getter @Setter
    @Column(name="ano", nullable = false)
    private int ano;

}
