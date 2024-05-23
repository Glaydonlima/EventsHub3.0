package com.eventshub.backend.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "servicos")
public class ServicoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String subcategoria;
    private String titulo;
    private String descricao;
    private double preco;
    private Boolean estaAtivo = true;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private PrestadorModelo prestador;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<SolicitacaoModelo> solicitacoes;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<FotoModelo> fotos;
}
