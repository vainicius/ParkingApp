package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="movimentacao_table", schema = "public")
public class Movimentacao extends AbstractEntity {
    @Getter @Setter
    @ManyToOne()
    @JoinColumn(nullable = false)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name="entradas",nullable = false)
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name="saidas")
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name="tempos")
    private LocalTime tempo;
    @Getter @Setter
    @Column(name="tempo_descontos")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name="tempos_multas")
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name="valores_descontos")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name="valores_multas")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name="valores_totais")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name="valores_horas")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name="valores_horas_multas")
    private BigDecimal valorHoraMulta;
    @Getter @Setter
    @Column(name = "tempo_estacionado")
    private long tempoEstacionadoMinutos;
    @Getter @Setter
    @Column(name = "tempo_multa")
    private long tempoMultaMinutos;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private long tempoDescontoMinutos;
}
