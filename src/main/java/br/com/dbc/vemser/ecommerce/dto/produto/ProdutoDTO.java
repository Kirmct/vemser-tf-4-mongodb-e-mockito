package br.com.dbc.vemser.ecommerce.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO extends ProdutoCreateDTO {

    private Integer idProduto;
}