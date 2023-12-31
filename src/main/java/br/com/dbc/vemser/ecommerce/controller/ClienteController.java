package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.ClienteControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.exceptions.UniqueFieldExistsException;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.service.ClienteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements ClienteControllerDoc {

    private final ClienteService clienteService;

    @GetMapping("/clientes-dados-completos")

    public ResponseEntity<List<ClienteDadosCompletosDTO>> buscarClientesDadosCompletos() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listarClientesComTodosOsDados(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(@Positive @RequestParam(required = false) Integer idCliente) throws Exception {
        return new ResponseEntity<>(clienteService.findAll(idCliente), HttpStatus.OK);
    }

    @GetMapping("/paginacao")
    public Page<ClientePaginadoDTO> listarClientePaginado(@PositiveOrZero(message = "O número da página deve ser maior ou igual a 0")  @RequestParam Integer pagina,
                                                          @Positive @RequestParam Integer quantidadeRegistros) {

        Sort ordenacao = Sort.by("nome").and(Sort.by("cpf"));

        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros, ordenacao);

        return clienteService.clientePaginado(pageable);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> getById(@Positive @PathVariable Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.getByid(idCliente), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> save(@Validated @RequestBody ClienteCreateDTO cliente) throws RegraDeNegocioException, UniqueFieldExistsException {
        return new ResponseEntity<>(clienteService.save(cliente), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@Positive @PathVariable Integer idCliente, @RequestBody ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.update(idCliente, cliente), HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Integer idCliente) {
        clienteService.delete(idCliente);
        return ResponseEntity.ok().build();
    }
}