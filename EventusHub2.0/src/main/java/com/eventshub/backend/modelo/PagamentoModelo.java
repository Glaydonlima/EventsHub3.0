package com.eventshub.backend.modelo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "pagamentos")
public class PagamentoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valorPago;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;

    private String formaPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private ClienteModelo cliente;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    @JsonIgnore
    private PrestadorModelo prestador;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    @JsonIgnore
    private ServicoModelo servico;

}
