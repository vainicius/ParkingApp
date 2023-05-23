package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import java.math.BigDecimal;
import java.time.LocalTime;

@Table(name="configuracao_table",schema = "public")
@Entity

public class Configuracao extends AbstractEntity {
    @NotNull(message = "O campo 'valorHora' não pode ser nulo!")
    @Getter @Setter
    @Column(name="valor_hora", nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name="valor_minuto_multa")
    private BigDecimal valorMinutoMulta;
    @NotNull(message = "O campo 'inicioExpediente' não pode ser nulo!")
    @Getter @Setter
    @Column(name="inicio_expediente", nullable = false)
    private LocalTime inicioExpediente;
    @NotNull(message = "O campo 'fimExpediente' não pode ser nulo!")
    @Getter @Setter
    @Column(name="fim_expediente", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name="tempo_para_desconto")
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name="tempo_de_desconto")
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name="gerar_desconto")
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name="vagas_moto")
    private int vagasMoto;
    @Getter @Setter
    @Column(name="vagas_carro")
    private int vagasCarro;
    @Getter @Setter
    @Column(name="vagas_van")
    private int vagasVan;




}
