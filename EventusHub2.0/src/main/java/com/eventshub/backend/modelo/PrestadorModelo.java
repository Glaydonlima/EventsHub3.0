package com.eventshub.backend.modelo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "prestadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorModelo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocial;

    @Column(unique = true)
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "A descrição da empresa é obrigatória")
    private String descricaoEmpresa;

    private String portfolio;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<ServicoModelo> servicos;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}


