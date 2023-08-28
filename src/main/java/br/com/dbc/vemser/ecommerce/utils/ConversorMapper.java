package br.com.dbc.vemser.ecommerce.utils;

import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class ConversorMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public static <TipoEntrada, TipoSaida> TipoSaida converter(TipoEntrada tipoConvertido, Class<TipoSaida> toValueType) {
        return objectMapper.convertValue(tipoConvertido, toValueType);
    }

    public static PedidoDTO converterPedido(PedidoEntity pedido){
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
        pedidoDTO.setCliente(pedido.getCliente());
        pedidoDTO.setProdutoEntities(pedido.getProdutoEntities());
        return pedidoDTO;
    }

}
