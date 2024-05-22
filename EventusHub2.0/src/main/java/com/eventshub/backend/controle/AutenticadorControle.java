package com.eventshub.backend.controle;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventshub.backend.dto.LoginRequestDTO;
import com.eventshub.backend.dto.RegisterRequestDTO;
import com.eventshub.backend.dto.ResponseDTO;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticadorControle {

    private final UsuarioRepositorio usuarioRepositorio;

    private final PasswordEncoder passwordEncoder;

    private final TokenServico tokenServico;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        UsuarioModelo usuario = this.usuarioRepositorio.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(passwordEncoder.matches(body.senha(), usuario.getSenha())){
            String token = this.tokenServico.geradorToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }



    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody RegisterRequestDTO body) {
        Optional<UsuarioModelo> usuario = this.usuarioRepositorio.findByEmail(body.email());

        if(usuario.isEmpty()) {
            UsuarioModelo novoUsuario = new UsuarioModelo();
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            novoUsuario.setEmail(body.email());
            novoUsuario.setNome(body.nome());
            this.usuarioRepositorio.save(novoUsuario);
            
             String token = this.tokenServico.geradorToken(novoUsuario);
            return ResponseEntity.ok(new ResponseDTO(novoUsuario.getNome(), token));
            }
        return ResponseEntity.badRequest().build();
    }

}
