package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.entity.*;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private ProdutoEntity produto;
    private ProdutoEntity produto1;
    private ProdutoEntity produto2;
    private ProdutoEntity produto3;
    private ProdutoEntity produto4;
    private List<ProdutoEntity> produtosList = new ArrayList<>();

    @BeforeEach
    void setUp() throws RegraDeNegocioException {
        produto = new ProdutoEntity();
        produto.setIdProduto(1);
        produto.setModelo("Camiseta Estampada");
        produto.setTamanho(TipoTamanho.M);
        produto.setCor("Azul");
        produto.setDescricao("Camiseta com estampa colorida");
        produto.setSetor(TipoSetor.MASCULINO);
        produto.setValor(30.0);
        produto.setImgUrl("https://www.example.com/camiseta1.jpg");

        produto1 = new ProdutoEntity();
        produto1.setIdProduto(2);
        produto1.setModelo("Calça Jeans");
        produto1.setTamanho(TipoTamanho.G);
        produto1.setCor("Preto");
        produto1.setDescricao("Calça jeans tradicional");
        produto1.setSetor(TipoSetor.MASCULINO);
        produto1.setValor(70.0);
        produto1.setImgUrl("https://www.example.com/calca-jeans.jpg");

        produto2 = new ProdutoEntity();
        produto2.setIdProduto(3);
        produto2.setModelo("Vestido Floral");
        produto2.setTamanho(TipoTamanho.P);
        produto2.setCor("Rosa");
        produto2.setDescricao("Vestido com estampa floral");
        produto2.setSetor(TipoSetor.FEMININO);
        produto2.setValor(50.0);
        produto2.setImgUrl("https://www.example.com/vestido-floral.jpg");

        produto3 = new ProdutoEntity();
        produto3.setIdProduto(4);
        produto3.setModelo("Sapato Social");
        produto3.setTamanho(TipoTamanho.M);
        produto3.setCor("Marrom");
        produto3.setDescricao("Sapato social elegante");
        produto3.setSetor(TipoSetor.MASCULINO);
        produto3.setValor(100.0);
        produto3.setImgUrl("https://www.example.com/sapato-social.jpg");

        produto4 = new ProdutoEntity();
        produto4.setIdProduto(5);
        produto4.setModelo("Saia Plissada");
        produto4.setTamanho(TipoTamanho.P);
        produto4.setCor("Verde");
        produto4.setDescricao("Saia plissada para ocasiões casuais");
        produto4.setSetor(TipoSetor.FEMININO);
        produto4.setValor(40.0);
        produto4.setImgUrl("https://www.example.com/saia-plissada.jpg");

        produtosList.add(produto);
        produtosList.add(produto1);
        produtosList.add(produto2);
        produtosList.add(produto3);
        produtosList.add(produto4);
    }

    @Test
    public void testListar() throws RegraDeNegocioException {
        when(produtoRepository.buscarTodosOptionalId(any())).thenReturn(produtosList);

        List<ProdutoDTO> result = produtoService.listar(1);

        assertNotNull(result);
        assertEquals(produtosList.size(), result.size());
    }


    @Test
    public void testListarTodosPorSetor() throws RegraDeNegocioException {
        when(produtoRepository.findAll()).thenReturn(produtosList);

        List<ProdutoDTO> produtosRetornados = produtoService.listarTodosPorSetor("MASCULINO");

        assertEquals(3, produtosRetornados.size());
    }


    @Test
    public void testBuscarProduto() throws RegraDeNegocioException {
        when(produtoRepository.findByIdProduto(any(Integer.class))).thenReturn(ConversorMapper.converter(produto, ProdutoEntity.class));

        ProdutoDTO produtoBuscado = produtoService.buscarProduto(1);


        // ASSERT
        Assertions.assertNotNull(produtoBuscado);
        Assertions.assertEquals(produto.getModelo(), produtoBuscado.getModelo());
        Assertions.assertEquals(produto.getTamanho(), produtoBuscado.getTamanho());
        Assertions.assertEquals(produto.getCor(), produtoBuscado.getCor());
        Assertions.assertEquals(produto.getSetor(), produtoBuscado.getSetor());
        Assertions.assertEquals(produto.getValor(), produtoBuscado.getValor());
        Assertions.assertEquals(produto.getImgUrl(), produtoBuscado.getImgUrl());
        Assertions.assertEquals(produto.getDescricao(), produtoBuscado.getDescricao());
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
        // ACT
        when(produtoRepository.save(any())).thenReturn(produto);

        // ACT
        ProdutoDTO produtoRetornado = produtoService.salvar(ConversorMapper.converter(produto, ProdutoCreateDTO.class));

        // ASSERT
        Assertions.assertNotNull(produtoRetornado);
        Assertions.assertEquals(produto.getModelo(), produtoRetornado.getModelo());
        Assertions.assertEquals(produto.getTamanho(), produtoRetornado.getTamanho());
        Assertions.assertEquals(produto.getCor(), produtoRetornado.getCor());
        Assertions.assertEquals(produto.getSetor(), produtoRetornado.getSetor());
        Assertions.assertEquals(produto.getValor(), produtoRetornado.getValor());
        Assertions.assertEquals(produto.getImgUrl(), produtoRetornado.getImgUrl());
        Assertions.assertEquals(produto.getDescricao(), produtoRetornado.getDescricao());
    }


    @Test
    public void testAtualizar() throws RegraDeNegocioException {
        // ACT
        when(produtoRepository.findByIdProduto(eq(2))).thenReturn(produto);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produto);

        // ACT
        ProdutoDTO produtoRetornado = produtoService.atualizar(2, ConversorMapper.converter(produto, ProdutoCreateDTO.class));

        // ASSERT
        Assertions.assertNotNull(produtoRetornado);
        Assertions.assertEquals(produto.getModelo(), produtoRetornado.getModelo());
        Assertions.assertEquals(produto.getTamanho(), produtoRetornado.getTamanho());
        Assertions.assertEquals(produto.getCor(), produtoRetornado.getCor());
        Assertions.assertEquals(produto.getSetor(), produtoRetornado.getSetor());
        Assertions.assertEquals(produto.getValor(), produtoRetornado.getValor());
        Assertions.assertEquals(produto.getImgUrl(), produtoRetornado.getImgUrl());
        Assertions.assertEquals(produto.getDescricao(), produtoRetornado.getDescricao());
    }


    @Test
    public void testDeletar() throws RegraDeNegocioException {

        when(produtoRepository.findByIdProduto(eq(2))).thenReturn(produto);

        produtoService.deletar(2);

        verify(produtoRepository, times(1)).delete(eq(produto));
    }

    @Test
    public void deveTestarIdNaoEncontrado() throws RegraDeNegocioException{
        Integer id = 10000;

        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.buscarProduto(id);
        });
    }

}
