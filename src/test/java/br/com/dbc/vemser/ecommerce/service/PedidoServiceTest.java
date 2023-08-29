//package br.com.dbc.vemser.ecommerce.service;
//
//import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoCreateDTO;
//import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
//import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoPaginacaoDTO;
//import br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO;
//import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
//import br.com.dbc.vemser.ecommerce.entity.*;
//import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
//import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
//import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.ecommerce.repository.*;
//import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PedidoServiceTest {
//
//    @InjectMocks
//    private PedidoService pedidoService;
//
//    //ARRANGE
//
//    @Mock
//    private HistoricoBuilder historicoBuilder;
//    @Mock
//    private PedidoRepository pedidoRepository;
//
//    @Mock
//    private UsuarioService usuarioService;
//
//    @Mock
//    private HistoricoRepository historicoRepository;
//
//    @Mock
//    private FinanceiroRepository financeiroRepository;
//
//    @Mock
//    private ProdutoMongoRepository produtoMongoRepository;
//
//    @Mock
//    private ClienteRepository clienteRepository;
//
//    @Mock
//    private ProdutoRepository produtoRepository;
//    private PedidoEntity pedido;
//    private PedidoEntity pedido1;
//    private ProdutoEntity produto;
//    private ProdutoEntity produto1;
//    private UsuarioEntity usuario;
//    private CargoEntity cargo;
//    private ClienteEntity cliente;
//    private Set<CargoEntity> cargoSet = new HashSet<>();
//    private List<ProdutoEntity> produtoList = new ArrayList<>();
//
//
//    @BeforeEach
//    void setUp() throws RegraDeNegocioException {
//        startPedido();
//    }
//
//    @Test
//    public void testeCriarPedido() throws RegraDeNegocioException {
//        PedidoCreateDTO pedidoCreateDTO = new PedidoCreateDTO();
//        pedidoCreateDTO.setIdProduto(1);
//
//        PedidoEntity pedidoCriado = new PedidoEntity();
//        pedidoCriado.setCliente(cliente);
//        pedidoCriado.setProdutoEntities(produtoList);
//
//        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
//        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoCriado);
//
//        PedidoDTO result = pedidoService.criarPedido(1, pedidoCreateDTO);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.getProdutoEntities(), produtoList);
//        Assertions.assertEquals(result.getCliente(), cliente);
//    }
//
//    @Test
//    public void testListar() throws RegraDeNegocioException {
//
//        List<PedidoEntity> pedidoList = new ArrayList<>();
//        pedidoList.add(pedido);
//        pedidoList.add(pedido1);
//
//        when(pedidoRepository.findAll()).thenReturn(pedidoList);
//
//        List<PedidoDTO> result = pedidoService.listar();
//
//        assertNotNull(result);
//        assertEquals(pedidoList.size(), result.size());
//    }
//
//    @Test
//    public void testeRealtorioPedido() throws RegraDeNegocioException {
//
//        List<RelatorioPedidoDTO> relatorioPedidoDTOS = new ArrayList<>();
//
//        relatorioPedidoDTOS.add(new RelatorioPedidoDTO("João", "joao@mail.com", 50.0, "N"));
//        relatorioPedidoDTOS.add(new RelatorioPedidoDTO("Maria", "maria@mail.com", 20.0, "N"));
//
//        when(pedidoRepository.relatorioPedido()).thenReturn(relatorioPedidoDTOS);
//
//        List<RelatorioPedidoDTO> result = pedidoService.relatorioPedido();
//
//        assertNotNull(result);
//        assertEquals(result.size(), relatorioPedidoDTOS.size());
//    }
//
//    @Test
//    public void testeBuscarByIdPedido() throws RegraDeNegocioException {
//        List<ProdutoEntity> produtoList = new ArrayList<>();
//
//        produtoList.add(produto1);
//
//
//        when(pedidoRepository.findById(any(Integer.class))).thenReturn(Optional.of(pedido));
//
//        PedidoDTO pedidoBuscado = pedidoService.buscarByIdPedido(1);
//
//        Assertions.assertNotNull(pedidoBuscado);
//        Assertions.assertEquals(pedidoBuscado.getIdPedido(), pedido.getIdPedido());
//        Assertions.assertEquals(pedidoBuscado.getCliente(), pedido.getCliente());
//        Assertions.assertEquals(pedidoBuscado.getValor(), pedido.getValor());
//        Assertions.assertEquals(pedidoBuscado.getStatusPedido(), pedido.getStatusPedido());
//        Assertions.assertEquals(pedidoBuscado.getProdutoEntities(), pedido.getProdutoEntities());
//        Assertions.assertEquals(pedidoBuscado.getQuantidadeProdutos(), pedido.getQuantidadeProdutos());
//    }
//
//
//    @Test
//    public void testeAdicionarProdutoAoPedido() throws RegraDeNegocioException {
//        PedidoEntity pedidoAchado = pedido;
//
//        List<ProdutoEntity> listProdutos = new ArrayList<>();
//        listProdutos.add(produto);
//        listProdutos.add(produto);
//
//        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
//        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoAchado);
//
//
//        pedidoService.adicionarProdutoAoPedido(1, 1);
//
//
//        Assertions.assertEquals(pedidoAchado.getProdutoEntities().size(), listProdutos.size());
//        Assertions.assertEquals(pedidoAchado.getIdPedido(), pedido.getIdPedido());
//    }
//
//    @Test
//    public void testeRemoverProdutoAoPedido() throws RegraDeNegocioException {
//        PedidoEntity pedidoAchado = pedido;
//
//        List<ProdutoEntity> listProdutos = new ArrayList<>();
//        listProdutos.add(produto1);
//
//        pedido.addProduto(produto1);
//
//        when(pedidoRepository.getById(1)).thenReturn((pedido));
//        when(produtoRepository.findByIdProduto(1)).thenReturn((produto));
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoAchado);
//
//
//        pedidoService.removerProdutoDoPedido(1, 1);
//
//
//        Assertions.assertEquals(pedidoAchado.getProdutoEntities(), listProdutos);
//        Assertions.assertEquals(pedidoAchado.getIdPedido(), pedido.getIdPedido());
//    }
//
//    @Test
//    public void testeDeletarPedido() throws RegraDeNegocioException{
//
//        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
//        pedidoService.deletePedido(1);
//
//        verify(pedidoRepository, times(1)).delete(eq(pedido));
//    }
//
//    @Test
//    public void testeAtualizarStatusPedido() throws RegraDeNegocioException{
//
//
//        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
//        when(clienteRepository.getById(1)).thenReturn(cliente);
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedido);
//
//
//        PedidoDTO result = pedidoService.atualizarStatusPedido(1);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.getStatusPedido(), "S");
//    }
//
//    @Test
//    public void testeAtualizarStatusPedidoComErro(){
//        Integer id = 1000;
//
//        assertThrows(RegraDeNegocioException.class, () -> {
//            pedidoService.buscarByIdPedido(id);
//        });
//
//    }
//
//    @Test
//    public void testarPaginacaoPedido() throws RegraDeNegocioException {
//        RelatorioPedidoDTO relatorioPedidoDTO = new RelatorioPedidoDTO();
//        relatorioPedidoDTO.setStatusPedido("N");
//        relatorioPedidoDTO.setValor(50.0);
//        relatorioPedidoDTO.setNome("Camiseta");
//        relatorioPedidoDTO.setEmail("mail@mail.com");
//
//
//        Page<RelatorioPedidoDTO> pedidoPage = new PageImpl<>(List.of(relatorioPedidoDTO));
//
//        Pageable pageable = PageRequest.of(0, pedidoPage.getSize());
//
//        when(pedidoRepository.buscarTodosRelatoriosPedidosPaginacao(pageable)).thenReturn(pedidoPage);
//
//        Page<RelatorioPedidoDTO> relatorioPedidoDTOS = pedidoService.listarRelatorioPaginado(pageable);
//        RelatorioPedidoDTO pedidoTeste = relatorioPedidoDTOS.getContent().get(0);
//
//        Assertions.assertNotNull(relatorioPedidoDTOS);
//        Assertions.assertEquals(pedidoTeste, relatorioPedidoDTO);
//    }
//
//
//    //setar valores padrao
//    private void startPedido() {
//        //cargo
//        cargo = new CargoEntity();
//        cargo.setIdCargo(1);
//        cargo.setNome("ROLE_VISITANTE");
//        //set cargos
//        cargoSet.add(cargo);
//
//        //usuario
//        usuario = new UsuarioEntity();
//        usuario.setIdUsuario(1);
//        usuario.setSenha("123");
//        usuario.setCargos(cargoSet);
//        //setusuario
//
//        cliente = new ClienteEntity();
//        cliente.setIdCliente(1);
//        cliente.setNome("Jõao");
//        cliente.setTelefone("999999999");
//        cliente.setEmail("joao@mail.com");
//        cliente.setCpf("12345678911");
//        cliente.setUsuario(usuario);
//
//        //produtos
//        produto = new ProdutoEntity();
//        produto.setIdProduto(1);
//        produto.setModelo("Camiseta Estampada");
//        produto.setTamanho(TipoTamanho.M);
//        produto.setCor("Azul");
//        produto.setDescricao("Camiseta com estampa colorida");
//        produto.setSetor(TipoSetor.MASCULINO);
//        produto.setValor(30.0);
//        produto.setImgUrl("https://www.example.com/camiseta1.jpg");
//        produtoList.add(produto);
//
//        produto1 = new ProdutoEntity();
//        produto1.setIdProduto(1);
//        produto1.setModelo("Camiseta Estampada");
//        produto1.setTamanho(TipoTamanho.M);
//        produto1.setCor("Azul");
//        produto1.setDescricao("Camiseta com estampa colorida");
//        produto1.setSetor(TipoSetor.MASCULINO);
//        produto1.setValor(30.0);
//        produto1.setImgUrl("https://www.example.com/camiseta1.jpg");
//
//        //pedidos
//        pedido = new PedidoEntity();
//        pedido.setIdPedido(1);
//        pedido.setCliente(cliente);
//        pedido.setValor(50.0);
//        pedido.setStatusPedido("N");
//        pedido.setProdutoEntities(produtoList);
//        pedido.setQuantidadeProdutos(1);
//
//        pedido1 = new PedidoEntity();
//        pedido1.setIdPedido(2);
//        pedido1.setCliente(cliente);
//        pedido1.setValor(40.0);
//        pedido1.setStatusPedido("N");
//        pedido1.setProdutoEntities(produtoList);
//        pedido1.setQuantidadeProdutos(1);
//    }
//
//}