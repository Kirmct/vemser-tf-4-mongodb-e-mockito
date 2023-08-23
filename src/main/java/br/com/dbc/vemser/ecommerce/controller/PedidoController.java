package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.PedidoControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.service.PedidoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController implements PedidoControllerDoc {

    private final PedidoService pedidoService;


    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listar() {
        return new ResponseEntity<>(pedidoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/relatorio-cliente-pedido")
    public ResponseEntity<List<RelatorioPedidoDTO>> listarClientesRelatorio() {
        return new ResponseEntity<>(pedidoService.relatorioPedido(), HttpStatus.OK);
    }

    @GetMapping("/relatorio-cliente-pedido-paginado")
    public Page<RelatorioPedidoDTO> listarRelatorioPaginado(Integer pagina,

                                                            Integer quantidadeRegistros) {

        Sort ordenacao = Sort.by("valor").descending()
                .and(Sort.by("statusPedido"));

        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros, ordenacao);

        return pedidoService.listarRelatorioPaginado(pageable);
    }

    @Override
    public ResponseEntity<PedidoDTO> buscarByIdPedido(Integer idPedido) throws RegraDeNegocioException {
        return new ResponseEntity<>(pedidoService.buscarByIdPedido(idPedido), HttpStatus.OK);
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable("idCliente") @Positive Integer idCliente,
                                                 @RequestBody @Valid PedidoCreateDTO idPedido) throws Exception {
        return new ResponseEntity<>(pedidoService.criarPedido(idCliente, idPedido), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PedidoDTO> atualizarStatusDoPedido(Integer idPedido) throws Exception {
        return new ResponseEntity<>(pedidoService.atualizarStatusPedido(idPedido), HttpStatus.OK);
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> removerPedido(@PathVariable("idPedido") @Positive Integer idPedido) throws Exception {

        pedidoService.deletePedido(idPedido);
        return ResponseEntity.ok().build();
    }

    // Implementação dos métodos da classe PedidoXProdutoService


    @PutMapping("/{idPedido}/carrinho/{idProduto}") //  add prod no pedido
    public ResponseEntity<Void> adicionarProdutoAoPedidoAtualizar(@PathVariable Integer idPedido,
                                                                  @PathVariable Integer idProduto)
            throws Exception {

        pedidoService.adicionarProdutoAoPedido(idPedido, idProduto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idPedido}/carrinho-remover/{idProduto}")
    public ResponseEntity<Void> removerProdutoDoPedido(@PathVariable Integer idPedido,
                                                       @PathVariable Integer idProduto) throws Exception {
        pedidoService.removerProdutoDoPedido(idPedido, idProduto);

        return ResponseEntity.ok().build();
    }

}
