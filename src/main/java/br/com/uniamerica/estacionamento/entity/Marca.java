package br.com.uniamerica.estacionamento.entity;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Table(name="marca_table",schema = "public")
@Entity
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Column(name="nome_marca", nullable = false, length = 15)
    private String nomeMarca;




}
