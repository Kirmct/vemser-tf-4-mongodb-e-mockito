package br.com.dbc.vemser.ecommerce.dto.financeiro;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import lombok.Data;

@Data
public class ProdutoVendidoFinanceiroDTO {

    private String id;
    private Integer idProduto;
    private Integer idPedido;
    private String modelo;
    private TipoTamanho tamanho;
    private String cor;
    private String descricao;
    private TipoSetor setor;
    private Double valor;
}
