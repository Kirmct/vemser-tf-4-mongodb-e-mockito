package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.FinancasControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoVendidoFinanceiro;
import br.com.dbc.vemser.ecommerce.service.FinanceiroProdutoService;
import br.com.dbc.vemser.ecommerce.service.FinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/financeiro")
public class FinancasController implements FinancasControllerDoc {

    private final FinanceiroService financeiroService;

    private final FinanceiroProdutoService financeiroProdutoService;


    @GetMapping("/pedidos-vendidos")
    public ResponseEntity<List<FinanceiroDTO>> listarTodos() {
        return new ResponseEntity<>(financeiroService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/produtos-vendidos")
    public ResponseEntity<List<ProdutoVendidoFinanceiroDTO>> listarTodosProdutos() {

        return new ResponseEntity<>(financeiroProdutoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/produtos-vendidos-setor")
    public ResponseEntity<List<FinanceiroPorSetorDTO>> listarTodosProdutosSetor() {

        return new ResponseEntity<>(financeiroProdutoService.totalVendasPorSetor(), HttpStatus.OK);
    }

    @GetMapping("/produtos-vendidos-contagem")
    public ResponseEntity<List<ProdutoVendidoCount>> produtosMaisVendidos() {

        return new ResponseEntity<>(financeiroProdutoService.produtosMaisVendidos(), HttpStatus.OK);
    }

}
