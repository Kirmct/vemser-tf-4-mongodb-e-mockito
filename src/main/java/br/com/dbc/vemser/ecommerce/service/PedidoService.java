package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.*;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.*;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    private final UsuarioService usuarioService;
    private final HistoricoRepository historicoRepository;

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    private final FinanceiroRepository financeiroRepository;
    private final ProdutoMongoRepository produtoMongoRepository;

    private final HistoricoBuilder historicoBuilder;

    @SneakyThrows
    private void addLog(String mensagem) {
        historicoBuilder.inserirHistorico(mensagem, Setor.PEDIDO);
    }

    private static void validacaoPedidoFinalizado(PedidoEntity pedidoAchado) throws RegraDeNegocioException {
        if (pedidoAchado.getStatusPedido().equalsIgnoreCase("S"))
            throw new RegraDeNegocioException("Pedido finalizado!");
    }


    public PedidoDTO criarPedido(Integer idCliente, PedidoCreateDTO idProduto) throws RegraDeNegocioException {

        Optional<ClienteEntity> clienteOP = clienteRepository.findById(idCliente);
        if (clienteOP.isEmpty()){
            addLog("Tentou criar um pedido para um cliente invalido: " + idCliente + ".");

            throw new RegraDeNegocioException("Cliente não encontrado.");
        }

        ClienteEntity cliente = clienteOP.get();

        Optional<ProdutoEntity> produtoEntityBuscadoOP = produtoRepository.findById(idProduto.getIdProduto());
        if (produtoEntityBuscadoOP.isEmpty()){
            addLog("Tentou criar um pedido passando um produto inválido: " + idProduto.getIdProduto() + ".");

            throw new RegraDeNegocioException("Produto não encontrado.");
        }

        ProdutoEntity produtoEntityBuscado = produtoEntityBuscadoOP.get();


        PedidoEntity pedido = new PedidoEntity();
        pedido.setStatusPedido("N");
        pedido.addProduto(produtoEntityBuscado);
        pedido.setCliente(cliente);


        PedidoDTO pedidoOutputDTO = ConversorMapper.converterPedido(pedidoRepository.save(pedido));

        addLog("Inseriu um pedido para o cliente: " + idCliente + ".");


        return pedidoOutputDTO;
    }

    public List<PedidoDTO> listar() throws RegraDeNegocioException {


        addLog("Listou os pedidos.");


        List<PedidoDTO> pedidoDTOS = new ArrayList<>();

        return pedidoRepository.findAll().stream()
                .map(p -> ConversorMapper.converterPedido(p)).toList();

    }

    public Page<RelatorioPedidoDTO> listarRelatorioPaginado(Pageable pageable) throws RegraDeNegocioException {

        addLog("Fez um relatório paginado de pedidos.");


        return pedidoRepository.buscarTodosRelatoriosPedidosPaginacao(pageable);

    }

    public List<RelatorioPedidoDTO> relatorioPedido() throws RegraDeNegocioException {

        addLog("Fez um relatório de pedidos.");


        return pedidoRepository.relatorioPedido();
    }

    public PedidoDTO buscarByIdPedido(Integer idPedido) throws RegraDeNegocioException {


        Optional<PedidoEntity> pedidoEntityOP = pedidoRepository.findById(idPedido);

        if (pedidoEntityOP.isEmpty()){
            addLog("Buscou por um pedido inválido: " + idPedido + ".");

            throw new RegraDeNegocioException("Pedido nao encontrado!");
        }

        PedidoEntity pedidoEntity = pedidoEntityOP.get();

        addLog("Buscou pelo pedido: " + idPedido + ".");


        PedidoDTO pedidoDTO = ConversorMapper.converterPedido(pedidoEntity);

        return pedidoDTO;

    }

    public Void adicionarProdutoAoPedido(Integer idPedido, Integer idProduto) throws RegraDeNegocioException {

        Optional<PedidoEntity> pedidoAchadoOP = pedidoRepository.findById(idPedido);

        if (pedidoAchadoOP.isEmpty()){
            addLog("Buscou por um pedido inválido: " + idPedido + ".");

            throw new RegraDeNegocioException("Pedido nao encontrado!");
        }
        PedidoEntity pedidoAchado = pedidoAchadoOP.get();

        validacaoPedidoFinalizado(pedidoAchado);

        Optional<ProdutoEntity> produtoEntityBuscadoOP = produtoRepository.findById(idProduto);
        if (produtoEntityBuscadoOP.isEmpty()){
            addLog("Tentou inserir um produto inválido: " + idProduto + ". Ao pedido: " + idPedido);

            throw new RegraDeNegocioException("Produto nao encontrado!");
        }

        ProdutoEntity produtoEntityBuscado = produtoEntityBuscadoOP.get();


        pedidoAchado.addProduto(produtoEntityBuscado);

        pedidoRepository.save(pedidoAchado);

        String msg = "Adicionou o produto: " + produtoEntityBuscado.getIdProduto() + ". Ao pedido: " + pedidoAchado.getIdPedido() + ".";
        addLog(msg);


        return null;

    }

    public Void removerProdutoDoPedido(Integer idPedido, Integer idProduto) throws RegraDeNegocioException {

        PedidoEntity pedidoAchado = pedidoRepository.getById(idPedido);
        if (pedidoAchado == null){
            addLog("Tentou remover de um pedido inválido: " + idPedido + ".");

            throw new RegraDeNegocioException("Pedido não encontrado!");
        }

        validacaoPedidoFinalizado(pedidoAchado);

        ProdutoEntity produtoEntityBuscado = produtoRepository.findByIdProduto(idProduto);
        if (produtoEntityBuscado == null) {
            addLog("Tentou remover um produto inválido: " + idProduto + ". Do pedido: " + idPedido);

            throw new RegraDeNegocioException("Produto não encontrado!");
        }

        pedidoAchado.removerProduto(produtoEntityBuscado);

        pedidoRepository.save(pedidoAchado);

        String msg = "Removeu o produto: " + produtoEntityBuscado.getIdProduto() + ". Do pedido: " + pedidoAchado.getIdPedido() + ".";
        addLog(msg);


        return null;

    }


    public void deletePedido(Integer idPedido) throws RegraDeNegocioException {


        Optional<PedidoEntity> pedidoEntityOP = pedidoRepository.findById(idPedido);
        if (pedidoEntityOP.isEmpty()){
            addLog("Tentou remover de um pedido inválido: " + idPedido + ".");

            throw new RegraDeNegocioException("Pedido não encontrado!");
        }

        PedidoEntity pedidoEntity = pedidoEntityOP.get();
        List<ProdutoEntity> produtos = new CopyOnWriteArrayList<>(pedidoEntity.getProdutoEntities());

        produtos.forEach(pedidoEntity::removerProduto);

        pedidoRepository.delete(pedidoEntity);

        String msg = "Deletou o pedido: " + idPedido +  ".";
        addLog(msg);


    }

    public PedidoDTO atualizarStatusPedido(Integer idPedido) throws RegraDeNegocioException {

        Optional<PedidoEntity> pedidoEntityOP = pedidoRepository.findById(idPedido);
        if (pedidoEntityOP.isEmpty()){
            addLog("Tentou atualizar um pedido inválido: " + idPedido + ".");

            throw new RegraDeNegocioException("Pedido não encontrado!");
        }

        PedidoEntity pedidoEntity = pedidoEntityOP.get();
        validacaoPedidoFinalizado(pedidoEntity);

        if (pedidoEntity.getStatusPedido().equalsIgnoreCase("N")) {
            pedidoEntity.setStatusPedido("S");
        }


        ClienteEntity byid = clienteRepository.getById(pedidoEntity.getCliente().getIdCliente());


        PedidoEntity save = pedidoRepository.save(pedidoEntity);


        PedidoDTO pedidoDTO = ConversorMapper.converterPedido(save);


        String msg = "Atualizou o status do pedido: " + idPedido +  ".";
        addLog(msg);


        addFinanceiro(pedidoDTO);

        return pedidoDTO;
    }

    private void addFinanceiro(PedidoDTO pedidoDTO) {
        FinanceiroEntity financeiro = new FinanceiroEntity();
        financeiro.setIdPedido(pedidoDTO.getIdPedido());
        financeiro.setTotal(pedidoDTO.getValor());
        financeiro.setDataDePagamento(LocalDate.now());
        Integer idPedido = pedidoDTO.getIdPedido();
        List<ProdutoVendidoFinanceiro> produtosVendidos = pedidoDTO.getProdutoEntities()
                .stream()
                .map(produto -> {
                    ProdutoVendidoFinanceiro produtoFinanceiroConvertido = ConversorMapper.converter(produto, ProdutoVendidoFinanceiro.class);
                    produtoFinanceiroConvertido.setIdPedido(idPedido);
                    return produtoFinanceiroConvertido;
                }).toList();

        produtoMongoRepository.saveAll(produtosVendidos);

        financeiroRepository.save(financeiro);
    }

}

