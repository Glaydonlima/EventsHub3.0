package com.eventshub.backend.controle;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.annotation.Secured;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.servico.UsuarioServico;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioControle {

  private final UsuarioServico usuarioServico;


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UsuarioModelo usuarioModelo) {
    return usuarioServico.login(usuarioModelo);
  }

  @Secured({"ROLE_CLIENTE", "ADMIN"})
  @DeleteMapping("/{usuarioId}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
    return usuarioServico.remover(id);
  }

  @Secured({"ROLE_CLIENTE", "ADMIN"})
  @PutMapping("/{id}")
  public ResponseEntity<?> alterar(@RequestBody UsuarioModelo usuarioModelo, HttpServletRequest request) {
    return usuarioServico.Alterar(usuarioModelo, request
    );
  }

  @PostMapping()
  public ResponseEntity<?> cadastar(@RequestBody @Valid UsuarioModelo usuarioModelo) {
    return usuarioServico.cadastrar(usuarioModelo);
  }

  @Secured({"ROLE_CLIENTE"})
  @GetMapping("/listar")
  public Iterable<UsuarioModelo> listar() {
    return usuarioServico.listar();
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handeValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();

      errors.put(fieldName, errorMessage);
    });
    return errors;
  }
}
