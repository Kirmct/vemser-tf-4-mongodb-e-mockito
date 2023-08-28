package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoMongoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class FinanceiroProdutoService  {

    private final ProdutoMongoRepository produtoMongoRepository;
    private final HistoricoBuilder historicoBuilder;

    @SneakyThrows
    private void addLog(String mensagem) {
        historicoBuilder.inserirHistorico(mensagem, Setor.FINANCEIRO);
    }

    public List<ProdutoVendidoFinanceiroDTO> findAll() {
        addLog("Buscou por todos os dados financeiros de produto");

        return produtoMongoRepository.findAll().stream().map(prodVendido ->
                ConversorMapper.converter(prodVendido, ProdutoVendidoFinanceiroDTO.class)).toList();
    }

    public List<FinanceiroPorSetorDTO> totalVendasPorSetor(){
        addLog("Buscou o valor total de vendas dos produto por setor");
        return produtoMongoRepository.totalVendasPorSetor();
    }

    public List<ProdutoVendidoCount> produtosMaisVendidos(){

        addLog("Buscou a quantidade total de vendas dos produto.");
        return produtoMongoRepository.produtosMaisVendidos().stream()
                .map(
                        prod -> new ProdutoVendidoCount(prod.getIdProduto(), prod.getQuantidade())
                ).collect(Collectors.toList());
    }
}
