package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
import br.com.dbc.vemser.ecommerce.entity.ProdutoVendidoFinanceiro;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoMongoRepository extends MongoRepository<ProdutoVendidoFinanceiro, String> {

    @Aggregation(pipeline = {
            "{$unwind: '$setor'}",
            "{$group: {_id: '$setor' ,totalValor: { $sum: '$valor' }}}"
    }
    )
    List<FinanceiroPorSetorDTO> totalVendasPorSetor();

    @Aggregation(pipeline = {
            "{'$unwind': '$idProduto' }",
            "{'$group': { '_id': '$idProduto', 'quantidade': { '$sum': 1 } } }"
    })
    List<ProdutoVendidoCount> produtosMaisVendidos();
}
