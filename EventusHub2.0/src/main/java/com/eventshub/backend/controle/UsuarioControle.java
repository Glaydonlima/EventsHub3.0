package com.eventshub.backend.controle;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioControle {

  @Autowired
  private UsuarioServico usuarioServico;


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UsuarioModelo usuarioModelo) {
    return usuarioServico.login(usuarioModelo);
  }

  @DeleteMapping("/remover/{id}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
    return usuarioServico.remover(id);
  }


  @PutMapping("/alterar/{id}")
  public ResponseEntity<?> alterar(@RequestBody UsuarioModelo usuarioModelo,
      @PathVariable Long id) {
    return usuarioServico.cadastrarAlterar(usuarioModelo, "alterar", id);
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<?> cadastar(@RequestBody @Valid UsuarioModelo usuarioModelo) {
    return usuarioServico.cadastrarAlterar(usuarioModelo, "cadastrar", null);
  }

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
