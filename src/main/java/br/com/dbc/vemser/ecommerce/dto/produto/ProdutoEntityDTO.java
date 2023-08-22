package br.com.dbc.vemser.ecommerce.dto.produto;


import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProdutoEntityDTO {
    private Integer idProduto;
    private String modelo;
    private TipoTamanho tamanho;
    private String cor;
    private String descricao;
    private TipoSetor setor;
    private Double valor;
    private String imgUrl;

    public ProdutoEntityDTO(Integer idProduto, String modelo, TipoTamanho tamanho, String cor, String descricao, TipoSetor setor, Double valor, String imgUrl) {
        this.idProduto = idProduto;
        this.modelo = modelo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.descricao = descricao;
        this.setor = setor;
        this.valor = valor;
        this.imgUrl = imgUrl;
    }
}
