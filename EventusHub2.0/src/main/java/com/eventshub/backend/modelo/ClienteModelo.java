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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ClienteModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O endereço não pode estar em branco")
    private String endereco;

    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos")
    private String telefone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data de nascimento não pode estar em branco")
    private Date dataNascimento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}
