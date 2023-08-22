package br.com.dbc.vemser.ecommerce.repository;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    ProdutoEntity findByIdProduto(Integer idProduto);

    @Query("Select p From PRODUTO p where p.setor = :setor")
    List<ProdutoEntity> buscarTodosPorSetor(String setor);

    @Query("Select p From PRODUTO p where (:idProduto is null or p.idProduto = :idProduto)")
    List<ProdutoEntity> buscarTodosOptionalId(Integer idProduto);

    @Query("Select new br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO(p.idProduto, p.modelo, " +
            "p.tamanho, p.cor, p.descricao, p.setor,  p.valor, p.imgUrl) from PRODUTO p")
    Page<ProdutoEntityDTO> buscarTodosProdutoPaginacao(Pageable pageable);

    @Query("Select new br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO(" +
            " p.modelo  , p.setor , p.valor) from PRODUTO p ")
    List<ProdutoRelatorioDTO> buscarProdutosRelatorio();

}
