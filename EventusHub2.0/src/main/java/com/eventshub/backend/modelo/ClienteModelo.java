package com.eventshub.backend.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class ClienteModelo {

    @Id
    private Long id;

    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos Ex: 81999999999")
    private String telefone;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data de nascimento não pode estar em branco")
    private LocalDate dataNascimento;
    
    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioModelo usuario;
    
    @NotBlank(message = "O endereço não pode estar em branco")
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagamentoModelo> pagamentos;

}
