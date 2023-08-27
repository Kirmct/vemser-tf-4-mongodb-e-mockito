package br.com.dbc.vemser.ecommerce.dto.pedido;


import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Integer idPedido;

    @JsonProperty("id_cliente")
    private ClienteEntity cliente;

    private Double valor;

    private String statusPedido;

    @JsonProperty("produtos")
    List<ProdutoEntity> produtoEntities;

    private Integer quantidadeProdutos;

}