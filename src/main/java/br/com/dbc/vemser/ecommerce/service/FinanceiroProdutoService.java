package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.entity.ProdutoVendidoFinanceiro;
import br.com.dbc.vemser.ecommerce.repository.ProdutoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FinanceiroProdutoService {

    private final ProdutoMongoRepository produtoMongoRepository;

    public List<ProdutoVendidoFinanceiro> findAll() {
        return produtoMongoRepository.findAll();
    }

}
