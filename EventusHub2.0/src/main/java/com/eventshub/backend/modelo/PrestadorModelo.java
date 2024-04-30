package com.eventshub.backend.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "prestador")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PrestadorModelo extends UsuarioModelo  {
    
    private String razaoSocial;
    private String cnpj;
    private String descricaoEmpresa;
    private String portfolio;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<ServicoModelo> servicos;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}



