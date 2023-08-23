package br.com.dbc.vemser.ecommerce.dto.pedido;

import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPaginacaoDTO {


    private Integer idPedido;

    private ClienteEntity cliente;

    private Double valor;

    private String statusPedido;

    private List<ProdutoEntity> produtos;


    private Integer quantidadeProdutos;

}