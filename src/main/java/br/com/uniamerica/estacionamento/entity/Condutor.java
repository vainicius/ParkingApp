package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name="condutor_table",schema = "public")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @Column(name="nome_condutor",nullable = false, length = 100)
    private String nomeCondutor;

    @Getter @Setter
    @Column(name="cpf",nullable = false,unique = true,length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name="telefone",nullable = false,unique = true,length = 17)
    private String telefone;

    @Getter @Setter
    @Column(name="tempo_gasto")
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name="tempo_desconto")
    private LocalTime tempoDesconto;

}
