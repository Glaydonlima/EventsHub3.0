package com.eventshub.backend.modelo;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "solicitacoes")
public class SolicitacaoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cliente_id")
    private ClienteModelo cliente;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "servico_id")
    private ServicoModelo servico;

    private double valorTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataExecucao;
    private String formaPagamento;
    private String status;
   

}
