package br.com.dbc.vemser.ecommerce.doc;


import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.exceptions.UniqueFieldExistsException;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

public interface ClienteControllerDoc {

    @Operation(summary = "Listar todos clientes", description = "Lista todos os clientes do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/clientes-dados-completos")
    ResponseEntity<List<ClienteDadosCompletosDTO>> buscarClientesDadosCompletos();


    @Operation(summary = "Listar todos clientes", description = "Lista todos os clientes do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<ClienteDTO>> findAll(@Positive @RequestParam(required = false) Integer idCliente) throws Exception;

    @Operation(summary = "Listar cliente por ID", description = "Lista cliente pelo ID no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cliente"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "ID não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}")
    ResponseEntity<ClienteDTO> getById(@Positive @PathVariable Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Criar um novo cliente", description = "Cria e adiciona um novo cliente no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o novo cliente"),
                    @ApiResponse(responseCode = "201", description = "Retorna o novo cliente"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "ID não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ClienteDTO> save(@Validated @RequestBody ClienteCreateDTO cliente) throws RegraDeNegocioException, UniqueFieldExistsException;

    @Operation(summary = "Atualizar o cliente pelo ID", description = "Atualiza o cliente informado pelo ID no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cliente atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "ID não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@Positive @PathVariable Integer idCliente,
                                             @RequestBody ClienteCreateDTO cliente) throws RegraDeNegocioException;

    @Operation(summary = "Deletar o cliente pelo ID", description = "Deleta o cliente informado pelo ID no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna Ok"),
                    @ApiResponse(responseCode = "204", description = "Retorna cliente deletado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "ID não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCliente}")
    ResponseEntity<Void> delete(@Positive @PathVariable Integer idCliente);


    @Operation(summary = "Listar todos clientes com paginação", description = "Lista todos os clientes do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes paginados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }


    )
    @GetMapping("/paginacao")
    public Page<ClientePaginadoDTO> listarClientePaginado(@Positive Integer pagina,
                                                          @Positive Integer quantidadeRegistros);

}
