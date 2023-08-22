package br.com.dbc.vemser.ecommerce.utils;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConverterProdutoParaDTOutil {

    @Autowired
    private ObjectMapper objectMapper;

    public ProdutoDTO converteProdutoParaDTO(ProdutoEntity produtoEntityUpdate) {
        return objectMapper.convertValue(produtoEntityUpdate, ProdutoDTO.class);
    }

    public ProdutoEntity converteDTOparaProduto(ProdutoCreateDTO produtoCreateDTO) {
        return objectMapper.convertValue(produtoCreateDTO, ProdutoEntity.class);
    }
}
