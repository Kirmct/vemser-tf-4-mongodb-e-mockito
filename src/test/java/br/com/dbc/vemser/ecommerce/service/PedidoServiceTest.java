package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.PedidoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    //ARRANGE
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void deveCriarPedidoNoBancoComSucesso() throws RegraDeNegocioException {

        PedidoCreateDTO pedidoCriado = new PedidoCreateDTO(1);

        ClienteEntity clienteMockado = new ClienteEntity(
                1,
                "Jeff",
                "83223094837",
                "email@teste.com",
                "11122233344",
                null,
                null,
                new UsuarioEntity()
        );

        List<ProdutoEntity> listProdutos = new ArrayList<>();
        ProdutoEntity produtoEntity = new ProdutoEntity(
                1,
                "Camiseta",
                TipoTamanho.G, "Preta",
                "Descricao",
                TipoSetor.MASCULINO,
                50.0,
                null,
                "url1");
        listProdutos.add(produtoEntity);

        PedidoEntity pedidoMockado = new PedidoEntity(1, null, 50.0, "N", listProdutos, 1 );
        PedidoDTO pedidoDTO = new PedidoDTO(1, clienteMockado, 50.00, "N", listProdutos, 1);

        when(pedidoRepository.save(any())).thenReturn(pedidoMockado);
//        when(ConversorMapper.converterPedido(pedidoMockado)).thenReturn(pedidoDTO);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteMockado));
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produtoEntity));

        // Act
        PedidoDTO resultado = pedidoService.criarPedido(1, pedidoCriado);

        // Assert
        assertNotNull(resultado);
        assertEquals(pedidoDTO.getIdPedido(), resultado.getIdPedido());
        assertEquals(pedidoDTO.getCliente(), resultado.getCliente());
        assertEquals(pedidoDTO.getValor(), resultado.getValor());
    }

}