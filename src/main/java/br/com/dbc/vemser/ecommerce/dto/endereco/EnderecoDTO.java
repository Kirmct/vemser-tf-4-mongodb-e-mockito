package br.com.dbc.vemser.ecommerce.dto.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO extends EnderecoCreateDTO {

    private Integer idCliente;
    private Integer idEndereco;
}