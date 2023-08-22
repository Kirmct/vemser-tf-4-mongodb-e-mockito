package br.com.dbc.vemser.ecommerce.dto.produto;


import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdutoRelatorioDTO {
    private String modelo;
    private TipoSetor setor;
    private Double valor;

    public ProdutoRelatorioDTO(String modelo, TipoSetor setor, Double valor) {
        this.modelo = modelo;
        this.setor = setor;
        this.valor = valor;
    }
}
