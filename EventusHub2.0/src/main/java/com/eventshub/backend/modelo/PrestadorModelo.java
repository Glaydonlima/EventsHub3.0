package com.eventshub.backend.modelo;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prestador")
@Data
public class PrestadorModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioModelo usuario;

    private String nome_empresa;

    private String descricao;

    private String telefone;
}



