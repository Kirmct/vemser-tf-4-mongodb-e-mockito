package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterProdutoParaDTOutil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ConverterProdutoParaDTOutil converterProdutoParaDTOutil;


    public List<ProdutoDTO> listar(Integer idProduto) {

        return produtoRepository.buscarTodosOptionalId(idProduto).stream()
                .map(converterProdutoParaDTOutil::converteProdutoParaDTO).toList();
    }

    public List<ProdutoDTO> listarTodosPorSetor(String setor) {

        return produtoRepository.findAll().stream()
                .filter(produto -> produto.getSetor().toString().equalsIgnoreCase(setor))
                .map(converterProdutoParaDTOutil::converteProdutoParaDTO).toList();
    }


    public Page<ProdutoEntityDTO> listarPaginado(Pageable pageable) {
        return produtoRepository.buscarTodosProdutoPaginacao(pageable);
    }

    public ProdutoDTO buscarProduto(Integer idProduto) throws RegraDeNegocioException {

        ProdutoEntity produtoEntity = produtoRepository.findByIdProduto(idProduto);

        if (produtoEntity == null) {
            throw new RegraDeNegocioException("Produto não cadastrado.");
        }

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntity);

    }

    public List<ProdutoRelatorioDTO> buscarProdutosRelatorio() {

        return produtoRepository.buscarProdutosRelatorio();

    }

    public ProdutoDTO salvar(ProdutoCreateDTO produtoCreateDTO) {

        ProdutoEntity produtoEntity = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        ProdutoEntity produtoEntityBuscado = produtoRepository.save(produtoEntity);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntityBuscado);
    }

    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {

        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);
        if (buscarProdutoEntity == null) {
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }
        ProdutoEntity produtoEntity = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        ProdutoEntity produtoEntityAtualizado = produtoRepository.save(produtoEntity);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntityAtualizado);
    }

    public void deletar(Integer idProduto) {

        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        produtoRepository.delete(buscarProdutoEntity);

    }


}
