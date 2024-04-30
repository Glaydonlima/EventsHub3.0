package com.eventshub.backend.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class ClienteModelo extends UsuarioModelo{

    private String endereco;
    private String telefone;
    private Date dataNascimento;

     @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}
