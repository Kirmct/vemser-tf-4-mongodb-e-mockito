package br.com.dbc.vemser.ecommerce.dto.pedido;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDTO {

    @NotNull
    @Positive(message = "O id precisa ser positivo!")
    @Schema(description = "O ID do produto associado ao pedido", required = true)
    private Integer idProduto;

}