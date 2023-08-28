package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (produtoCreateDTO == null) {
            addLog("Tentou criar um produto invalido.");
            throw new RegraDeNegocioException("Dados do produto inválidos.");
        }

        if (produtoCreateDTO.getModelo() == null || produtoCreateDTO.getModelo().isEmpty()) {
            addLog("Tentou criar um produto com modelo vazio.");
            throw new RegraDeNegocioException("Modelo do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getTamanho() == null) {
            addLog("Tentou criar um produto com Tamanho vazio.");
            throw new RegraDeNegocioException("Tamanho do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getCor() == null || produtoCreateDTO.getCor().isEmpty()) {
            addLog("Tentou criar um produto com Cor vazio.");
            throw new RegraDeNegocioException("Cor do produto não pode estar vazia.");
        }

        if (produtoCreateDTO.getSetor() == null) {
            addLog("Tentou criar um produto com Setor vazio.");
            throw new RegraDeNegocioException("Setor do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getValor() == null || produtoCreateDTO.getValor() <= 0) {
            addLog("Tentou criar um produto com Valor vazio.");
            throw new RegraDeNegocioException("Valor do produto inválido.");
        }

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);
        ProdutoEntity produtoEntitySalvo = produtoRepository.save(produtoEntity);

        addLog("Cadastrou um novo produto. Modelo: " + produtoCreateDTO.getModelo() + ".");

        return ConversorMapper.converter(produtoEntitySalvo, ProdutoDTO.class);
    }


    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        if (buscarProdutoEntity == null) {
            addLog("Tentou atualizar um produto não cadastrado.");
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }

        if (produtoCreateDTO == null) {
            addLog("Tentou atualizar um produto invalido.");
            throw new RegraDeNegocioException("Dados do produto inválidos.");
        }

        if (produtoCreateDTO.getModelo() == null || produtoCreateDTO.getModelo().isEmpty()) {
            addLog("Tentou criar um produto com modelo vazio.");
            throw new RegraDeNegocioException("Modelo do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getTamanho() == null) {
            addLog("Tentou criar um produto com Tamanho vazio.");
            throw new RegraDeNegocioException("Tamanho do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getCor() == null || produtoCreateDTO.getCor().isEmpty()) {
            addLog("Tentou criar um produto com Cor vazio.");
            throw new RegraDeNegocioException("Cor do produto não pode estar vazia.");
        }

        if (produtoCreateDTO.getSetor() == null) {
            addLog("Tentou criar um produto com Setor vazio.");
            throw new RegraDeNegocioException("Setor do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getValor() == null || produtoCreateDTO.getValor() <= 0) {
            addLog("Tentou criar um produto com Valor vazio.");
            throw new RegraDeNegocioException("Valor do produto inválido.");
        }

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);

        ProdutoEntity produtoEntityAtualizado = produtoRepository.save(produtoEntity);

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
