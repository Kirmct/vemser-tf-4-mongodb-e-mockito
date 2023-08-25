package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
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

    private final UsuarioService usuarioService;
    private final HistoricoRepository historicoRepository;

    private Historico inserirHistorico(String msg) throws RegraDeNegocioException {

        UsuarioLogadoDTO usuarioLogadoDTO = usuarioService.getLoggedUser();

        Historico historico = new Historico();

        if (usuarioLogadoDTO.getIdUsuario() != null){
            Integer idUsuario = usuarioService.getIdLoggedUser();
            String cargo = usuarioService.findByRole(idUsuario);
            historico.setCargo(Cargo.valueOf(cargo));
            historico.setUsuario(usuarioLogadoDTO.getLogin() + ".");
        }else {
            historico.setCargo(Cargo.valueOf("ROLE_VISITANTE"));
            historico.setUsuario("Visitante");
        }
        historico.setAcao(msg);
        historico.setDataAcao(LocalDateTime.now());
        return historico;
    }

    public List<ProdutoDTO> listar(Integer idProduto) throws RegraDeNegocioException {
        Historico historico = inserirHistorico("Buscou todos os produtos!");
        historicoRepository.save(historico);

        return produtoRepository.buscarTodosOptionalId(idProduto).stream()
                .map(produto -> ConversorMapper.converter(produto, ProdutoDTO.class)).toList();
    }

    public List<ProdutoDTO> listarTodosPorSetor(String setor) throws RegraDeNegocioException {
        Historico historico = inserirHistorico("Buscou produtos por setor!");
        historicoRepository.save(historico);

        return produtoRepository.findAll().stream()
                .filter(produto -> produto.getSetor().toString().equalsIgnoreCase(setor))
                .map(produto -> ConversorMapper.converter(produto, ProdutoDTO.class)).toList();
    }

    public Page<ProdutoEntityDTO> listarPaginado(Pageable pageable) throws RegraDeNegocioException {

        Historico historico = inserirHistorico("Buscou produtos paginados!");
        historicoRepository.save(historico);

        return produtoRepository.buscarTodosProdutoPaginacao(pageable);
    }

    public ProdutoDTO buscarProduto(Integer idProduto) throws RegraDeNegocioException {

        ProdutoEntity produtoEntity = produtoRepository.findByIdProduto(idProduto);

        if (produtoEntity == null) {
            Historico historico = inserirHistorico("Tentou buscar produto inexistente!");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Produto não cadastrado.");
        }

        Historico historico = inserirHistorico("Buscou produto por id!");
        historicoRepository.save(historico);

        return ConversorMapper.converter(produtoEntity, ProdutoDTO.class);

    }

    public List<ProdutoRelatorioDTO> buscarProdutosRelatorio() throws RegraDeNegocioException {
        Historico historico = inserirHistorico("Buscou produtos relatorio!");
        historicoRepository.save(historico);

        return produtoRepository.buscarProdutosRelatorio();

    }

    public ProdutoDTO salvar(ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        if (produtoCreateDTO == null) {
            Historico historico = inserirHistorico("Tentou criar um produto invalido.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Dados do produto inválidos.");
        }

        if (produtoCreateDTO.getModelo() == null || produtoCreateDTO.getModelo().isEmpty()) {
            Historico historico = inserirHistorico("Tentou criar um produto com modelo vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Modelo do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getTamanho() == null) {
            Historico historico = inserirHistorico("Tentou criar um produto com Tamanho vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Tamanho do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getCor() == null || produtoCreateDTO.getCor().isEmpty()) {
            Historico historico = inserirHistorico("Tentou criar um produto com Cor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Cor do produto não pode estar vazia.");
        }

        if (produtoCreateDTO.getSetor() == null) {
            Historico historico = inserirHistorico("Tentou criar um produto com Setor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Setor do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getValor() == null || produtoCreateDTO.getValor() <= 0) {
            Historico historico = inserirHistorico("Tentou criar um produto com Valor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Valor do produto inválido.");
        }

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);
        ProdutoEntity produtoEntitySalvo = produtoRepository.save(produtoEntity);

        Historico historico = inserirHistorico("Cadastrou um novo produto. Modelo: " + produtoCreateDTO.getModelo() + ".");
        historicoRepository.save(historico);

        return ConversorMapper.converter(produtoEntitySalvo, ProdutoDTO.class);
    }


    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        if (buscarProdutoEntity == null) {
            Historico historico = inserirHistorico("Tentou atualizar um produto não cadastrado.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }

        if (produtoCreateDTO == null) {
            Historico historico = inserirHistorico("Tentou atualizar um produto invalido.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Dados do produto inválidos.");
        }

        if (produtoCreateDTO.getModelo() == null || produtoCreateDTO.getModelo().isEmpty()) {
            Historico historico = inserirHistorico("Tentou criar um produto com modelo vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Modelo do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getTamanho() == null) {
            Historico historico = inserirHistorico("Tentou criar um produto com Tamanho vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Tamanho do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getCor() == null || produtoCreateDTO.getCor().isEmpty()) {
            Historico historico = inserirHistorico("Tentou criar um produto com Cor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Cor do produto não pode estar vazia.");
        }

        if (produtoCreateDTO.getSetor() == null) {
            Historico historico = inserirHistorico("Tentou criar um produto com Setor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Setor do produto não pode estar vazio.");
        }

        if (produtoCreateDTO.getValor() == null || produtoCreateDTO.getValor() <= 0) {
            Historico historico = inserirHistorico("Tentou criar um produto com Valor vazio.");
            historicoRepository.save(historico);
            throw new RegraDeNegocioException("Valor do produto inválido.");
        }

        ProdutoEntity produtoEntity = ConversorMapper.converter(produtoCreateDTO, ProdutoEntity.class);

        ProdutoEntity produtoEntityAtualizado = produtoRepository.save(produtoEntity);

        return ConversorMapper.converter(produtoEntityAtualizado, ProdutoDTO.class);
    }

    public void deletar(Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        if (buscarProdutoEntity == null) {
            Historico historico = inserirHistorico("Tentou deletar um produto não cadastrado.");
            historicoRepository.save(historico);
            return;
        }

        produtoRepository.delete(buscarProdutoEntity);

        Historico historico = inserirHistorico("Produto deletado com sucesso!");
        historicoRepository.save(historico);
    }


}
