package com.eventshub.backend.modelo;

import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class UsuarioModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;

    @Column(unique =  true)
    protected String email;
    
    protected String senha;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreatedDate
    protected Date dataCriacao;

    public UsuarioModelo() {
        this.dataCriacao = new Date(); 
    }
    

}
