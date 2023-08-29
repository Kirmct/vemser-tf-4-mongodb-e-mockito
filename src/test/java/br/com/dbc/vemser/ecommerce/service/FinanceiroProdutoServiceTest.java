//package br.com.dbc.vemser.ecommerce.service;
//
//import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
//import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoCount;
//import br.com.dbc.vemser.ecommerce.dto.financeiro.ProdutoVendidoFinanceiroDTO;
//import br.com.dbc.vemser.ecommerce.entity.ProdutoVendidoFinanceiro;
//import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
//import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
//import br.com.dbc.vemser.ecommerce.repository.ProdutoMongoRepository;
//import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
//import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class FinanceiroProdutoServiceTest {
//
//    @InjectMocks
//    private FinanceiroProdutoService financeiroService;
//    @Mock
//    private  ProdutoMongoRepository mongoRepository;
//
//    @Mock
//    private HistoricoBuilder historicoBuilder;
//
//    private ProdutoVendidoFinanceiro produtoVendidoFinanceiro;
//
//    private FinanceiroPorSetorDTO financeiroPorSetorDTO;
//
//    private ProdutoVendidoCount produtoVendidoCount;
//
//
//    @BeforeEach
//    void setUp() {
//        //financeiro vendido
//        produtoVendidoFinanceiro = new ProdutoVendidoFinanceiro(
//                "1", 1, 1, "Camiseta", TipoTamanho.M,
//                "Azul", "Manga curta", TipoSetor.MASCULINO, 50.0
//        );
//
//        //financeiro setor
//        financeiroPorSetorDTO = new FinanceiroPorSetorDTO(TipoSetor.UNISSEX, 50.0);
//
//        produtoVendidoCount = new ProdutoVendidoCount(1, 2);
//
//    }
//
//    @Test
//    void findAll() {
//        List<ProdutoVendidoFinanceiro> listProdutosVenditos = new ArrayList<>();
//        listProdutosVenditos.add(produtoVendidoFinanceiro);
//
//        when(mongoRepository.findAll()).thenReturn(listProdutosVenditos);
//
//        List<ProdutoVendidoFinanceiroDTO> result =  financeiroService.findAll();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.get(0).getIdProduto(), listProdutosVenditos.get(0).getIdProduto());
//        Assertions.assertEquals(result.size(), listProdutosVenditos.size());
//    }
//
//    @Test
//    void totalVendasPorSetor() {
//        List<FinanceiroPorSetorDTO> listFinanceiroPorSetorDTO = new ArrayList<>();
//        listFinanceiroPorSetorDTO.add(financeiroPorSetorDTO);
//
//        when(mongoRepository.totalVendasPorSetor()).thenReturn(listFinanceiroPorSetorDTO);
//
//        List<FinanceiroPorSetorDTO> result = financeiroService.totalVendasPorSetor();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.get(0).getSetor(), listFinanceiroPorSetorDTO.get(0).getSetor());
//        Assertions.assertEquals(result.size(), listFinanceiroPorSetorDTO.size());
//    }
//
//    @Test
//    void produtosMaisVendidos() {
//        List<ProdutoVendidoCount> listCount = new ArrayList<>();
//        listCount.add(produtoVendidoCount);
//
//        when(mongoRepository.produtosMaisVendidos()).thenReturn(listCount);
//
//        List<ProdutoVendidoCount> result = financeiroService.produtosMaisVendidos();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.get(0).getIdProduto(), listCount.get(0).getIdProduto());
//        Assertions.assertEquals(result.size(), listCount.size());
//    }
//}