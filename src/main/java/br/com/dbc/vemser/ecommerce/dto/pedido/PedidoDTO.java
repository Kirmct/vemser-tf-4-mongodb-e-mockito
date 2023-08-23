package br.com.dbc.vemser.ecommerce.dto.pedido;


import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {


    List<ProdutoEntity> produtos;
    private Integer idPedido;
    private Integer idCliente;
    private Double valor;
    private String statusPedido;

    private Integer quantidadeProdutos;

}