package br.com.dbc.vemser.ecommerce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class ConversorMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <TipoEntrada, TipoSaida> TipoSaida converter(TipoEntrada tipoConvertido, Class<TipoSaida> toValueType) {
        return objectMapper.convertValue(tipoConvertido, toValueType);
    }

}
