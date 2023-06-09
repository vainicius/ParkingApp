package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="veiculo_table",schema = "public")
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @Column(name="placa", nullable = false, length = 15)
    private String placa;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="modelo_id",nullable = false)
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
