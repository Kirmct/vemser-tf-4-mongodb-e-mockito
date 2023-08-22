package br.com.dbc.vemser.ecommerce.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientePaginadoDTO {

    private Integer idCliente;

    private String nome;

    private String telefone;

    private String email;

    private String cpf;


}