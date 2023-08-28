package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
import br.com.dbc.vemser.ecommerce.repository.ProdutoMongoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class FinanceiroProdutoService {

    private final ProdutoMongoRepository produtoMongoRepository;

    public List<ProdutoVendidoFinanceiroDTO> findAll() {

        return produtoMongoRepository.findAll().stream().map(prodVendido ->
                ConversorMapper.converter(prodVendido, ProdutoVendidoFinanceiroDTO.class)).toList();
    }

    public List<FinanceiroPorSetorDTO> totalVendasPorSetor(){

        return produtoMongoRepository.totalVendasPorSetor();
    }

    public List<ProdutoVendidoCount> produtosMaisVendidos(){
        return produtoMongoRepository.produtosMaisVendidos().stream()
                .map(
                        prod -> new ProdutoVendidoCount(prod.getIdProduto(), prod.getQuantidade())
                ).collect(Collectors.toList());
    }
}
