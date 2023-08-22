package br.com.dbc.vemser.ecommerce.utils;


import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConverterPedidoParaDTOutil {

    @Autowired
    private ObjectMapper objectMapper;

    public PedidoDTO converterPedidooParaDTO(PedidoEntity pedido) {

        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
        pedidoDTO.setIdCliente(pedido.getCliente().getIdCliente());
        pedidoDTO.setProdutos(pedido.getProdutoEntities());

        return pedidoDTO;
    }
}
