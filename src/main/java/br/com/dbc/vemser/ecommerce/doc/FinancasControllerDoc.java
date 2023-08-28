package br.com.dbc.vemser.ecommerce.doc;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface FinancasControllerDoc {

    @Operation(summary = "Lista todos os pedidos finalizados", description = "Lista resumos dos pedidos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de dados do pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/pedidos-vendidos")
    public ResponseEntity<List<FinanceiroDTO>> listarTodos();



    @Operation(summary = "Lista resumo de todos os produtos vendidos", description = "Lista resumos dos produtos vendidos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de dados do produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produtos-vendidos")
    public ResponseEntity<List<ProdutoVendidoFinanceiroDTO>> listarTodosProdutos();

    @Operation(summary = "Lista valor de todos os produtos vendidos por setor", description = "Lista de valor dos pedidos vendidos por determinado setor")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de valor do produto por setor"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produtos-vendidos-setor")
    public ResponseEntity<List<FinanceiroPorSetorDTO>> listarTodosProdutosSetor();


    @Operation(summary = "Lista da quantidade de determinado produto", description = "Lista a quantidade de produto vendido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna quantidade de produtos vendidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Página não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produtos-vendidos-contagem")
    public ResponseEntity<List<ProdutoVendidoCount>> produtosMaisVendidos();
}
