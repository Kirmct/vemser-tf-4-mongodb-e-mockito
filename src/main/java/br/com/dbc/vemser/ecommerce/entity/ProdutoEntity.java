package br.com.dbc.vemser.ecommerce.entity;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUTO")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "SEQ_PRODUTO", allocationSize = 1)
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "MODELO")
//    @NotBlank(message = "O modelo não pode estar em branco")
    private String modelo;

    @Column(name = "TAMANHO")
//    @NotNull(message = "O tamanho não pode estar em branco")
    @Enumerated(EnumType.STRING)
    private TipoTamanho tamanho;

    @Column(name = "COR")
//    @NotBlank(message = "A cor não pode estar em branco")
    private String cor;

    @Column(name = "DESCRICAO")
//    @NotBlank(message = "A descrição não pode estar em branco")
    private String descricao;

    @Column(name = "SETOR")
//    @NotNull(message = "O setor não pode estar em branco")
    @Enumerated(EnumType.STRING)
    private TipoSetor setor;

    @Column(name = "VALOR")
//    @NotNull(message = "O valor não pode estar em branco")
//    @Positive(message = "O valor deve ser positivo")
    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtoEntities", cascade = CascadeType.ALL)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    @Column(name = "IMG_URL")
    private String imgUrl;

    public void addPedido(PedidoEntity pedidoEntity) {

        pedidos.add(pedidoEntity);
    }

    public void removePedido(PedidoEntity pedidoEntity) {

        pedidos.remove(pedidoEntity);
    }

}