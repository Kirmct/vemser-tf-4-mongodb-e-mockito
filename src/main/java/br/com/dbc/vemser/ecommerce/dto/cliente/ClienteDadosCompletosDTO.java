package br.com.dbc.vemser.ecommerce.dto.cliente;

import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDadosCompletosDTO {

    private Integer idCliente;

    private String nome;

    private String telefone;

    private String email;

    private String cpf;

    private List<EnderecoDTO> enderecoEntities;

    private List<PedidoDTO> pedidoEntities;
}
