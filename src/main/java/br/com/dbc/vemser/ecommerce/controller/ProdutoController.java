package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.ProdutoControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoEntityDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoRelatorioDTO;
import br.com.dbc.vemser.ecommerce.service.ProdutoService;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController implements ProdutoControllerDoc {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(
            @RequestParam(required = false) Integer idProduto) throws Exception {


        return new ResponseEntity<>(produtoService.listar(idProduto), HttpStatus.OK);
    }



    @GetMapping("listar-setor/{setor}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorSetor(
            @PathVariable("setor") @NotBlank String setor) {


        return new ResponseEntity<>(produtoService.listarTodosPorSetor(setor), HttpStatus.OK);
    }



    @GetMapping("/relatorio-produto/")
    public ResponseEntity<List<ProdutoRelatorioDTO>> listarRelatorioProduto() {

        return new ResponseEntity<>(produtoService.buscarProdutosRelatorio(), HttpStatus.OK);
    }


    @GetMapping("paginacao")
    public Page<ProdutoEntityDTO> listarProdutosPaginados(
            Integer pagina,
            Integer quantidadeRegistros) throws Exception {

        Sort ordenacao = Sort.by("valor");

        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros, ordenacao);

        return produtoService.listarPaginado(pageable);
    }


    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> buscarProduto(
            @Positive(message = "O número precisa ser positivo.")
            @PathVariable Integer idProduto) throws Exception {

        ProdutoDTO produtoDTO = produtoService.buscarProduto(idProduto);
        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvarProduto(
            @Valid @RequestBody
            ProdutoCreateDTO produtoCreateDTO) throws Exception {


        ProdutoDTO produtoDTO = produtoService.salvar(produtoCreateDTO);

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @Positive(message = "O número precisa ser positivo.")
            @PathVariable Integer idProduto,
            @Valid @RequestBody
            ProdutoCreateDTO produtoCreateDTO) throws Exception {


        ProdutoDTO produtoDTO = produtoService.atualizar(idProduto, produtoCreateDTO);

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> delete(@PathVariable Integer idProduto) throws Exception {
        produtoService.deletar(idProduto);
        return ResponseEntity.ok().build();
    }

}