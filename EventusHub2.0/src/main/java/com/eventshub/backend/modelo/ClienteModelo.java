package com.eventshub.backend.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ClienteModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private Date dataNascimento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}
