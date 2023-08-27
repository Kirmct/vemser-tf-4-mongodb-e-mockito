package br.com.dbc.vemser.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PEDIDO")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity cliente;

    @Column(name = "valor")
    private Double valor = 0d;

    @Column(name = "pago")
    private String statusPedido;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Pedido_X_Produto",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private List<ProdutoEntity> produtoEntities = new ArrayList<>();

    @Column(name = "quantidade_produtos")
    private Integer quantidadeProdutos = 0;

    public void addProduto(ProdutoEntity produtoEntity) {
        produtoEntity.addPedido(this);
        produtoEntities.add(produtoEntity);
        atualizarQuantidadeProdutos();
        this.valor += produtoEntity.getValor();

    }

    public void removerProduto(ProdutoEntity produtoEntity) {
        Double valorProduto = produtoEntity.getValor();
        boolean remove = produtoEntities.remove(produtoEntity);
        atualizarQuantidadeProdutos();
        if (remove) {
            this.valor -= valorProduto;
            produtoEntity.removePedido(this);
        }
    }

    public void atualizarQuantidadeProdutos(){

        this.quantidadeProdutos = this.produtoEntities.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoEntity that = (PedidoEntity) o;
        return Objects.equals(idPedido, that.idPedido) && Objects.equals(cliente, that.cliente) && Objects.equals(valor, that.valor) && Objects.equals(statusPedido, that.statusPedido) && Objects.equals(produtoEntities, that.produtoEntities) && Objects.equals(quantidadeProdutos, that.quantidadeProdutos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, cliente, valor, statusPedido, produtoEntities, quantidadeProdutos);
    }
}

