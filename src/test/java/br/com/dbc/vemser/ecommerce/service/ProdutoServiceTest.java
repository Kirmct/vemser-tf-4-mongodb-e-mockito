package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testListar() throws RegraDeNegocioException {
        List<ProdutoEntity> produtosList = new ArrayList<>();

        ProdutoEntity produto1 = new ProdutoEntity();
        produto1.setIdProduto(1);
        produto1.setModelo("Camiseta Estampada");
        produto1.setTamanho(TipoTamanho.M);
        produto1.setCor("Azul");
        produto1.setDescricao("Camiseta com estampa colorida");
        produto1.setSetor(TipoSetor.MASCULINO);
        produto1.setValor(30.0);
        produto1.setImgUrl("https://www.example.com/camiseta1.jpg");

        ProdutoEntity produto2 = new ProdutoEntity();
        produto2.setIdProduto(2);
        produto2.setModelo("Calça Jeans");
        produto2.setTamanho(TipoTamanho.G);
        produto2.setCor("Preto");
        produto2.setDescricao("Calça jeans tradicional");
        produto2.setSetor(TipoSetor.MASCULINO);
        produto2.setValor(70.0);
        produto2.setImgUrl("https://www.example.com/calca-jeans.jpg");

        ProdutoEntity produto3 = new ProdutoEntity();
        produto3.setIdProduto(3);
        produto3.setModelo("Vestido Floral");
        produto3.setTamanho(TipoTamanho.P);
        produto3.setCor("Rosa");
        produto3.setDescricao("Vestido com estampa floral");
        produto3.setSetor(TipoSetor.FEMININO);
        produto3.setValor(50.0);
        produto3.setImgUrl("https://www.example.com/vestido-floral.jpg");

        ProdutoEntity produto4 = new ProdutoEntity();
        produto4.setIdProduto(4);
        produto4.setModelo("Sapato Social");
        produto4.setTamanho(TipoTamanho.M);
        produto4.setCor("Marrom");
        produto4.setDescricao("Sapato social elegante");
        produto4.setSetor(TipoSetor.MASCULINO);
        produto4.setValor(100.0);
        produto4.setImgUrl("https://www.example.com/sapato-social.jpg");

        ProdutoEntity produto5 = new ProdutoEntity();
        produto5.setIdProduto(5);
        produto5.setModelo("Saia Plissada");
        produto5.setTamanho(TipoTamanho.P);
        produto5.setCor("Verde");
        produto5.setDescricao("Saia plissada para ocasiões casuais");
        produto5.setSetor(TipoSetor.FEMININO);
        produto5.setValor(40.0);
        produto5.setImgUrl("https://www.example.com/saia-plissada.jpg");

        produtosList.add(produto1);
        produtosList.add(produto2);
        produtosList.add(produto3);
        produtosList.add(produto4);
        produtosList.add(produto5);

        when(produtoRepository.buscarTodosOptionalId(any())).thenReturn(produtosList);

        List<ProdutoDTO> result = produtoService.listar(1);

        assertNotNull(result);
        assertEquals(produtosList.size(), result.size());
    }


    @Test
    public void testListarTodosPorSetor() throws RegraDeNegocioException {
        List<ProdutoEntity> produtosList = new ArrayList<>();

        ProdutoEntity produto1 = new ProdutoEntity();
        produto1.setIdProduto(1);
        produto1.setModelo("Camiseta Estampada");
        produto1.setTamanho(TipoTamanho.M);
        produto1.setCor("Azul");
        produto1.setDescricao("Camiseta com estampa colorida");
        produto1.setSetor(TipoSetor.MASCULINO);
        produto1.setValor(30.0);
        produto1.setImgUrl("https://www.example.com/camiseta1.jpg");

        ProdutoEntity produto2 = new ProdutoEntity();
        produto2.setIdProduto(2);
        produto2.setModelo("Calça Jeans");
        produto2.setTamanho(TipoTamanho.G);
        produto2.setCor("Preto");
        produto2.setDescricao("Calça jeans tradicional");
        produto2.setSetor(TipoSetor.MASCULINO);
        produto2.setValor(70.0);
        produto2.setImgUrl("https://www.example.com/calca-jeans.jpg");

        ProdutoEntity produto3 = new ProdutoEntity();
        produto3.setIdProduto(3);
        produto3.setModelo("Vestido Floral");
        produto3.setTamanho(TipoTamanho.P);
        produto3.setCor("Rosa");
        produto3.setDescricao("Vestido com estampa floral");
        produto3.setSetor(TipoSetor.FEMININO);
        produto3.setValor(50.0);
        produto3.setImgUrl("https://www.example.com/vestido-floral.jpg");

        ProdutoEntity produto4 = new ProdutoEntity();
        produto4.setIdProduto(4);
        produto4.setModelo("Sapato Social");
        produto4.setTamanho(TipoTamanho.M);
        produto4.setCor("Marrom");
        produto4.setDescricao("Sapato social elegante");
        produto4.setSetor(TipoSetor.MASCULINO);
        produto4.setValor(100.0);
        produto4.setImgUrl("https://www.example.com/sapato-social.jpg");

        ProdutoEntity produto5 = new ProdutoEntity();
        produto5.setIdProduto(5);
        produto5.setModelo("Saia Plissada");
        produto5.setTamanho(TipoTamanho.P);
        produto5.setCor("Verde");
        produto5.setDescricao("Saia plissada para ocasiões casuais");
        produto5.setSetor(TipoSetor.FEMININO);
        produto5.setValor(40.0);
        produto5.setImgUrl("https://www.example.com/saia-plissada.jpg");

        produtosList.add(produto1);
        produtosList.add(produto2);
        produtosList.add(produto3);
        produtosList.add(produto4);
        produtosList.add(produto5);

        when(produtoRepository.findAll()).thenReturn(produtosList);

        List<ProdutoDTO> produtosRetornados = produtoService.listarTodosPorSetor("MASCULINO");

        assertEquals(3, produtosRetornados.size());
    }


    @Test
    public void testBuscarProduto() throws RegraDeNegocioException {
        ProdutoDTO produtoMockado = new ProdutoDTO();
        produtoMockado.setIdProduto(2);
        produtoMockado.setModelo("Camiseta manga longa");
        produtoMockado.setTamanho(TipoTamanho.P);
        produtoMockado.setCor("Preto");
        produtoMockado.setSetor(TipoSetor.MASCULINO);
        produtoMockado.setValor(29.90);
        produtoMockado.setImgUrl("https://www.example.com/camiseta.jpg");
        produtoMockado.setDescricao("Se adapta bem ao corpo.");

        when(produtoRepository.findByIdProduto(any(Integer.class))).thenReturn(ConversorMapper.converter(produtoMockado, ProdutoEntity.class));

        ProdutoDTO produtoBuscado = produtoService.buscarProduto(1);


        // ASSERT
        Assertions.assertNotNull(produtoBuscado);
        Assertions.assertEquals(produtoMockado.getModelo(), produtoBuscado.getModelo());
        Assertions.assertEquals(produtoMockado.getTamanho(), produtoBuscado.getTamanho());
        Assertions.assertEquals(produtoMockado.getCor(), produtoBuscado.getCor());
        Assertions.assertEquals(produtoMockado.getSetor(), produtoBuscado.getSetor());
        Assertions.assertEquals(produtoMockado.getValor(), produtoBuscado.getValor());
        Assertions.assertEquals(produtoMockado.getImgUrl(), produtoBuscado.getImgUrl());
        Assertions.assertEquals(produtoMockado.getDescricao(), produtoBuscado.getDescricao());
    }

    @Test
    public void testBuscarProdutosRelatorio() throws RegraDeNegocioException {
        List<ProdutoRelatorioDTO> produtosSimulados = new ArrayList<>();
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Goku", TipoSetor.MASCULINO, 20.0));
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Batman", TipoSetor.FEMININO, 25.0));
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Bob Esponja", TipoSetor.MASCULINO, 35.0));
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Patrick", TipoSetor.FEMININO, 41.0));
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Flash", TipoSetor.FEMININO, 87.0));
        produtosSimulados.add(new ProdutoRelatorioDTO("Camiseta Zoom", TipoSetor.FEMININO, 50.0));


        when(produtoRepository.buscarProdutosRelatorio()).thenReturn(produtosSimulados);

        List<ProdutoRelatorioDTO> produtosRelatorio = produtoService.buscarProdutosRelatorio();

        // Verificar o resultado
        assertNotNull(produtosRelatorio);
        assertEquals(produtosSimulados.size(), produtosRelatorio.size());

    }

    @Test
    public void testSalvar() throws RegraDeNegocioException {
        ProdutoCreateDTO produtoCriado = new ProdutoCreateDTO(
                "Camiseta",
                TipoTamanho.M,
                "Azul",
                TipoSetor.MASCULINO,
                50.0,
                "https://www.example.com/camiseta.jpg",
                "Uma camiseta incrível"
        );

        ProdutoEntity produtoMockado = new ProdutoEntity();
        produtoMockado.setModelo(produtoCriado.getModelo());
        produtoMockado.setTamanho(produtoCriado.getTamanho());
        produtoMockado.setCor(produtoCriado.getCor());
        produtoMockado.setSetor(produtoCriado.getSetor());
        produtoMockado.setValor(produtoCriado.getValor());
        produtoMockado.setImgUrl(produtoCriado.getImgUrl());
        produtoMockado.setDescricao(produtoCriado.getDescricao());

        // ACT
        when(produtoRepository.save(any())).thenReturn(produtoMockado);

        // ACT
        ProdutoDTO produtoRetornado = produtoService.salvar(produtoCriado);

        // ASSERT
        Assertions.assertNotNull(produtoRetornado);
        Assertions.assertEquals(produtoCriado.getModelo(), produtoRetornado.getModelo());
        Assertions.assertEquals(produtoCriado.getTamanho(), produtoRetornado.getTamanho());
        Assertions.assertEquals(produtoCriado.getCor(), produtoRetornado.getCor());
        Assertions.assertEquals(produtoCriado.getSetor(), produtoRetornado.getSetor());
        Assertions.assertEquals(produtoCriado.getValor(), produtoRetornado.getValor());
        Assertions.assertEquals(produtoCriado.getImgUrl(), produtoRetornado.getImgUrl());
        Assertions.assertEquals(produtoCriado.getDescricao(), produtoRetornado.getDescricao());
    }


    @Test
    public void testAtualizar() throws RegraDeNegocioException {
        ProdutoCreateDTO produtoCriado = new ProdutoCreateDTO(
                "Camiseta",
                TipoTamanho.M,
                "Azul",
                TipoSetor.MASCULINO,
                50.0,
                "https://www.example.com/camiseta.jpg",
                "Uma camiseta incrível"
        );

        ProdutoEntity produtoMockado = new ProdutoEntity();
        produtoMockado.setIdProduto(2);
        produtoMockado.setModelo(produtoCriado.getModelo());
        produtoMockado.setTamanho(produtoCriado.getTamanho());
        produtoMockado.setCor(produtoCriado.getCor());
        produtoMockado.setSetor(produtoCriado.getSetor());
        produtoMockado.setValor(produtoCriado.getValor());
        produtoMockado.setImgUrl(produtoCriado.getImgUrl());
        produtoMockado.setDescricao(produtoCriado.getDescricao());

        // ACT
        when(produtoRepository.findByIdProduto(eq(2))).thenReturn(produtoMockado);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoMockado);

        // ACT
        ProdutoDTO produtoRetornado = produtoService.atualizar(2, produtoCriado);

        // ASSERT
        Assertions.assertNotNull(produtoRetornado);
        Assertions.assertEquals(produtoCriado.getModelo(), produtoRetornado.getModelo());
        Assertions.assertEquals(produtoCriado.getTamanho(), produtoRetornado.getTamanho());
        Assertions.assertEquals(produtoCriado.getCor(), produtoRetornado.getCor());
        Assertions.assertEquals(produtoCriado.getSetor(), produtoRetornado.getSetor());
        Assertions.assertEquals(produtoCriado.getValor(), produtoRetornado.getValor());
        Assertions.assertEquals(produtoCriado.getImgUrl(), produtoRetornado.getImgUrl());
        Assertions.assertEquals(produtoCriado.getDescricao(), produtoRetornado.getDescricao());
    }


    @Test
    public void testDeletar() throws RegraDeNegocioException {
        ProdutoCreateDTO produtoCriado = new ProdutoCreateDTO(
                "Camiseta",
                TipoTamanho.M,
                "Azul",
                TipoSetor.MASCULINO,
                50.0,
                "https://www.example.com/camiseta.jpg",
                "Uma camiseta incrível"
        );

        ProdutoEntity produtoMockado = new ProdutoEntity();
        produtoMockado.setIdProduto(2);
        produtoMockado.setModelo(produtoCriado.getModelo());
        produtoMockado.setTamanho(produtoCriado.getTamanho());
        produtoMockado.setCor(produtoCriado.getCor());
        produtoMockado.setSetor(produtoCriado.getSetor());
        produtoMockado.setValor(produtoCriado.getValor());
        produtoMockado.setImgUrl(produtoCriado.getImgUrl());
        produtoMockado.setDescricao(produtoCriado.getDescricao());

        when(produtoRepository.findByIdProduto(eq(2))).thenReturn(produtoMockado);

        produtoService.deletar(2);

        verify(produtoRepository, times(1)).delete(eq(produtoMockado));
    }

}
