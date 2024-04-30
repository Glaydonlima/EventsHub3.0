package com.eventshub.backend.modelo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
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
    private ClienteModelo cliente;
    
    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private PrestadorModelo prestador;
    
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoModelo servico;
    
}
