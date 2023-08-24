package br.com.dbc.vemser.ecommerce.doc;

import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoDTO;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface HistoricoControllerDoc {

    @Operation(summary = "Listar todo o Histórico.", description = "Lista todos os históricos do banco.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de históricos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<HistoricoDTO>> findAll();

    @Operation(summary = "Retorna um histórico por Id.", description = "Busca no banco por um histórico que conrresponde ao Id passado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um histórico"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idHistorico}")
    ResponseEntity<HistoricoDTO> findById(@PathVariable("idHistorico") String idHistorico) throws RegraDeNegocioException;
}
