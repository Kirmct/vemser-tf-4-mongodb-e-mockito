package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
import br.com.dbc.vemser.ecommerce.repository.ProdutoMongoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FinanceiroProdutoService {

    private final ProdutoMongoRepository produtoMongoRepository;

    public List<ProdutoVendidoFinanceiroDTO> findAll() {

        return produtoMongoRepository.findAll().stream().map(prodVendido ->
                ConversorMapper.converter(prodVendido, ProdutoVendidoFinanceiroDTO.class)).toList();
    }

}
