package br.com.dbc.vemser.ecommerce.dto.financeiro;

import lombok.*;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoVendidoCount {

    @Id
    private Integer idProduto;
    private Integer quantidade;
}
