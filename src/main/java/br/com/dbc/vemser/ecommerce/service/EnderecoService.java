package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public List<EnderecoDTO> listarEnderecos() throws Exception {
        List<EnderecoEntity> enderecos = enderecoRepository.findAll();
        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();

        for (EnderecoEntity endereco : enderecos) {
            enderecoDTOS.add(converterByEnderecoDTO(endereco));
        }
        return enderecoDTOS;
    }

    public EnderecoDTO getEnderecoById(Integer idEndereco) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(Math.toIntExact(idEndereco));
        if (enderecoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        return converterByEnderecoDTO(enderecoOpt.get());
    }

    public List<EnderecoDTO> listarEnderecoByIdCliente(Integer idCliente) throws Exception {
        List<EnderecoEntity> enderecos = enderecoRepository
                .findEnderecoEntityByCliente_IdCliente(idCliente);
        if (enderecos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum endereço encontrado para o cliente");
        }

        return enderecos.stream()
                .map(this::converterByEnderecoDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO create(Integer idCliente, EnderecoCreateDTO enderecoCreateDTO) throws Exception {

        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(idCliente);


        if (clienteEntity.isEmpty()) {
            throw new RegraDeNegocioException("Cliente não encontrado");
        }

        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
        entity.setCliente(clienteEntity.get());

        EnderecoEntity enderecoCreated = enderecoRepository.save(entity);

        return converterByEnderecoDTO(enderecoCreated);
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(idEndereco);
        if (enderecoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        EnderecoEntity endereco = enderecoOpt.get();

        enderecoCreateDTO.setIdCliente(endereco.getCliente().getIdCliente());
        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
        entity.setIdEndereco(idEndereco);

        EnderecoEntity enderecoUpdated = enderecoRepository.save(entity);

        EnderecoDTO enderecoDTO = converterByEnderecoDTO(enderecoUpdated);

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(idEndereco);
        if (enderecoOpt.isPresent()) {
            EnderecoEntity endereco = enderecoOpt.get();
            enderecoRepository.delete(endereco);
        }
    }

    public EnderecoDTO converterByEnderecoDTO(EnderecoEntity endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdCliente(endereco.getCliente().getIdCliente());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setBairro(endereco.getBairro());
        return enderecoDTO;
    }

    public EnderecoEntity converterByEndereco(EnderecoCreateDTO enderecoCreateDTO) {
        EnderecoEntity entity = new EnderecoEntity();
        entity.setNumero(enderecoCreateDTO.getNumero());
        entity.setLogradouro(enderecoCreateDTO.getLogradouro());
        entity.setComplemento(enderecoCreateDTO.getComplemento());
        entity.setCep(enderecoCreateDTO.getCep());
        entity.setCidade(enderecoCreateDTO.getCidade());
        entity.setEstado(enderecoCreateDTO.getEstado());
        entity.setBairro(enderecoCreateDTO.getBairro());
        return entity;
    }


}
