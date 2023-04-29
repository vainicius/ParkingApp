package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name="modelo_table",schema = "public")
@Entity
public class Modelo extends AbstractEntity {

    @Getter @Setter
    @Column(name="nome_modelo", nullable = false, length = 30)
    private String nomeModelo;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="",nullable = false)
    private Marca marca;


}
