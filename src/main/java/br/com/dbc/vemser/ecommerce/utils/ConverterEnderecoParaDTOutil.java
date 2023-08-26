package br.com.dbc.vemser.ecommerce.utils;


import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoUpdateDTO;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Data
@Component
public class ConverterEnderecoParaDTOutil {

    private final ObjectMapper objectMapper;

    public EnderecoDTO converterByEnderecoDTO(EnderecoEntity endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdCliente(endereco.getCliente().getIdCliente());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setBairro(endereco.getBairro());
        return enderecoDTO;
    }

    public EnderecoEntity converterByEndereco(EnderecoCreateDTO enderecoCreateDTO) {
        EnderecoEntity entity = objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);

        return entity;
    }

    public EnderecoEntity converterEndUpdateByEndereco(EnderecoUpdateDTO enderecoUpdateDTO) {
        EnderecoEntity entity = objectMapper.convertValue(enderecoUpdateDTO, EnderecoEntity.class);
        return entity;
    }
}
