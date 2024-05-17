package com.eventshub.backend.controle;

import com.eventshub.backend.modelo.FotoModelo;
import com.eventshub.backend.modelo.ServicoModelo;
import com.eventshub.backend.servico.ServicoServico;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "*")
public class ServicoControle {

  @Autowired
  private ServicoServico servicoServico;

  @PostMapping
  public ServicoModelo criarServico(@RequestBody ServicoModelo servico) {
    return servicoServico.salvarServico(servico);
  }

  @PostMapping("/{servicoId}/fotos")
  public FotoModelo adicionarFoto(@PathVariable Long servicoId, @RequestBody FotoModelo foto) {
    ServicoModelo servico = servicoServico.obterServicoPorId(servicoId);
    foto.setServico(servico);
    return servicoServico.salvarFoto(foto);
  }

  @GetMapping("/{servicoId}/fotos")
  public List<FotoModelo> obterFotos(@PathVariable Long servicoId) {
    return servicoServico.obterFotosPorServico(servicoId);
  }

}
