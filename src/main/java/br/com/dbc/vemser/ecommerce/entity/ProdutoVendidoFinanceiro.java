package br.com.dbc.vemser.ecommerce.entity;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "financeiro_de_produto")
public class ProdutoVendidoFinanceiro {

    @Id
    private String id;

    private Integer idProduto;

    private Integer idPedido;

    private String modelo;

    private TipoTamanho tamanho;

    private String cor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoSetor setor;

    private Double valor;

}
