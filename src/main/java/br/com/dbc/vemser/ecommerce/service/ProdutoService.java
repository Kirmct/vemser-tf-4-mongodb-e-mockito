package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final HistoricoBuilder historicoBuilder;

    private void addLog(String mensagem) throws RegraDeNegocioException {
       historicoBuilder.inserirHistorico(mensagem, Setor.PRODUTO);
    }

    public List<ProdutoDTO> listar(Integer idProduto) throws RegraDeNegocioException {
        addLog("Buscou todos os produtos!");

        return produtoRepository.buscarTodosOptionalId(idProduto).stream()
                .map(produto -> ConversorMapper.converter(produto, ProdutoDTO.class)).toList();
    }

    public List<ProdutoDTO> listarTodosPorSetor(String setor) throws RegraDeNegocioException {
        addLog("Buscou produtos por setor!");

        return produtoRepository.findAll().stream()
                .filter(produto -> produto.getSetor().toString().equalsIgnoreCase(setor))
                .map(produto -> ConversorMapper.converter(produto, ProdutoDTO.class)).toList();
    }

    public Page<ProdutoEntityDTO> listarPaginado(Pageable pageable) throws RegraDeNegocioException {

        addLog("Buscou produtos paginados!");

        return produtoRepository.buscarTodosProdutoPaginacao(pageable);
    }

    public ProdutoDTO buscarProduto(Integer idProduto) throws RegraDeNegocioException {

        ProdutoEntity produtoEntity = produtoRepository.findByIdProduto(idProduto);

        if (produtoEntity == null) {
            addLog("Tentou buscar produto inexistente!");
            throw new RegraDeNegocioException("Produto não cadastrado.");
        }

        addLog("Buscou produto por id!");

        return ConversorMapper.converter(produtoEntity, ProdutoDTO.class);

    }

    public List<ProdutoRelatorioDTO> buscarProdutosRelatorio() throws RegraDeNegocioException {
        addLog("Buscou produtos relatorio!");

        return produtoRepository.buscarProdutosRelatorio();

    }

    public ProdutoDTO salvar(ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);
        ProdutoEntity produtoEntitySalvo = produtoRepository.save(produtoEntity);

        addLog("Cadastrou um novo produto. Modelo: " + produtoCreateDTO.getModelo() + ".");

        return ConversorMapper.converter(produtoEntitySalvo, ProdutoDTO.class);
    }


    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);

        ProdutoEntity produtoEntityAtualizado = produtoRepository.save(produtoEntity);
        addLog("Atualizou um novo produto. Modelo: " + produtoCreateDTO.getModelo() + ".");
        return ConversorMapper.converter(produtoEntityAtualizado, ProdutoDTO.class);
    }

    public void deletar(Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        if (buscarProdutoEntity == null) {
            addLog("Tentou deletar um produto não cadastrado.");
            return;
        }

        produtoRepository.delete(buscarProdutoEntity);

        addLog("Produto deletado com sucesso!");
    }


}
