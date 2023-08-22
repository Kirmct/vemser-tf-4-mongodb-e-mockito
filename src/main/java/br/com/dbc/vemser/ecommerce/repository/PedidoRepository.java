package br.com.dbc.vemser.ecommerce.repository;


import br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {


    @Query("select new br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO(c.nome, c.email, p.valor, p.statusPedido ) " +
            "FROM PEDIDO p " +
            "left join p.cliente c")
    Page<RelatorioPedidoDTO> buscarTodosRelatoriosPedidosPaginacao(Pageable pageable);

    @Query("select new br.com.dbc.vemser.ecommerce.dto.pedido.RelatorioPedidoDTO(c.nome, c.email, p.valor, p.statusPedido ) " +
            "FROM PEDIDO p " +
            "left join p.cliente c")
    List<RelatorioPedidoDTO> relatorioPedido();

}