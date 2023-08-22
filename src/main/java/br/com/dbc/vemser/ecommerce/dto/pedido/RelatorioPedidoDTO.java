package br.com.dbc.vemser.ecommerce.dto.pedido;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioPedidoDTO {

    private String nome;

    private String email;

    private Double valor;

    private String statusPedido;


}
