package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name="movimentacao_table")
@Entity
public class Movimentacao extends AbstractEntity {
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn()
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="",nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name="entradas",nullable = false)
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name="saidas",nullable = false)
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name="tempos",nullable = false)
    private LocalTime tempo;
    @Getter @Setter
    @Column(name="tempo_descontos",nullable = false)
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name="tempos_multas",nullable = false)
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name="valores_descontos",nullable = false)
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name="valores_multas",nullable = false)
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name="valores_totais",nullable = false)
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name="valores_horas",nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name="valores_horas_multas",nullable = false)
    private BigDecimal valorHoraMulta;

}
