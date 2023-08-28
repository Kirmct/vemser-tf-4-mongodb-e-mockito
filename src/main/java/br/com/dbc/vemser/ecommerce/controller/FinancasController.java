package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.entity.FinanceiroEntity;
import br.com.dbc.vemser.ecommerce.entity.ProdutoVendidoFinanceiro;
import br.com.dbc.vemser.ecommerce.service.FinanceiroProdutoService;
import br.com.dbc.vemser.ecommerce.service.FinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/financeiro")
public class FinancasController {

    private final FinanceiroService financeiroService;

    private final FinanceiroProdutoService financeiroProdutoService;

    @GetMapping("/pedidos-vendidos")
    public List<FinanceiroEntity> listarTodos() {
        return financeiroService.findAll();
    }

    @GetMapping("/produtos-vendidos")
    public List<ProdutoVendidoFinanceiro> listarTodosProdutos() {
        return financeiroProdutoService.findAll();
    }

}
