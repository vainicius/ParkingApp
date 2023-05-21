package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name="modelo_table",schema = "public")
@Entity
public class Modelo extends AbstractEntity {

    @NotNull(message = "O campo 'nomeModelo' não pode ser nulo!")
    @Size(min = 3, max = 30, message = "Tamanho mínimo: 3. Tamanho Máximo: 50")
    @Getter @Setter
    @Column(name="nome_modelo", nullable = false, length = 30)
    private String nomeModelo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="",nullable = false)
    @Getter @Setter
    private Marca marca;


}
