package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.HistoricoControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoContadorDTO;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoDTO;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController implements HistoricoControllerDoc {

    private final HistoricoService historicoService;

    @GetMapping
    public ResponseEntity<List<HistoricoDTO>> findAll(){
        return new ResponseEntity<>(historicoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idHistorico}")
    public ResponseEntity<HistoricoDTO> findById(@PathVariable("idHistorico") String idHistorico) throws RegraDeNegocioException {
        return new ResponseEntity<>(historicoService.findById(idHistorico), HttpStatus.OK);
    }

    @GetMapping("/by-cargo")
    public ResponseEntity<List<HistoricoDTO>> findByCargo(Cargo cargo){
        return new ResponseEntity<>(historicoService.findByCargo(cargo), HttpStatus.OK);
    }

    @GetMapping("/group-and-count-by-cargo")
    public ResponseEntity<List<HistoricoContadorDTO>> groupByCargoAndCount() {
        return new ResponseEntity<>(historicoService.groupByCargoAndCount(), HttpStatus.OK);
    }

    @GetMapping("/by-setor")
    public ResponseEntity<List<HistoricoDTO>> findBySetor(Setor setor){
        return new ResponseEntity<>(historicoService.findBySetor(setor), HttpStatus.OK);
    }

}
