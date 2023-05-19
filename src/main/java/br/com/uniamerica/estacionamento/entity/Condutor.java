package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

@Entity
@Table(name="condutor_table",schema = "public")
public class Condutor extends AbstractEntity {


    @Size(min = 3, max = 50, message = "Tamanho mínimo: 3. Tamanho Máximo: 50")
    @Getter @Setter
    @Column(name="nome_condutor",nullable = false, length = 50)
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
