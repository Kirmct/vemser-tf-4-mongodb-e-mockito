package br.com.dbc.vemser.ecommerce.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    private Integer idEndereco;


    private Integer idCliente;


    private String logradouro;

    private Integer numero;

    private String complemento;

    private String cep;

    private String cidade;

    private String estado;

    private String bairro;
}