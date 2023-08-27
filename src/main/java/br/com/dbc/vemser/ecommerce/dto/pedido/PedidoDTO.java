package br.com.dbc.vemser.ecommerce.dto.pedido;


import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Integer idPedido;
    private Integer idCliente;
    private Double valor;
    private String statusPedido;
    private List<ProdutoEntity> produtoEntities;

    private Integer quantidadeProdutos;

}