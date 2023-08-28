package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final HistoricoRepository historicoRepository;
    private final HistoricoBuilder historicoBuilder;

    private void addLog(String mensagem) throws RegraDeNegocioException {
        Historico historico = historicoBuilder.inserirHistorico(mensagem, Setor.ENDERECO);
        historicoRepository.save(historico);
    }

    public List<EnderecoDTO> listarEnderecos() throws RegraDeNegocioException {
        List<EnderecoEntity> enderecos = enderecoRepository.findAll();

        List<EnderecoDTO> enderecoDTOS = enderecos.stream()
                .map(ConverterEnderecoParaDTOutil::converterByEnderecoDTO).toList();

        addLog("Realizando listagem de endereços");

        return enderecoDTOS;
    }

    public EnderecoDTO getEnderecoById(Integer idEndereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoOpt = enderecoRepository
                .findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        return ConverterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoOpt);
    }

    public List<EnderecoDTO> listarEnderecoByIdCliente(Integer idCliente) throws RegraDeNegocioException {
        List<EnderecoEntity> enderecos = enderecoRepository
                .findEnderecoEntityByCliente_IdCliente(idCliente);
        if (enderecos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum endereço encontrado para o cliente");
        }

        addLog("Realizando listagem de endereços por cliente");

        return enderecos.stream()
                .map(ConverterEnderecoParaDTOutil::converterByEnderecoDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO create(Integer idCliente, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {

        ClienteEntity clienteEntity = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));


        EnderecoEntity entity = ConverterEnderecoParaDTOutil.converterByEndereco(enderecoCreateDTO);
        entity.setCliente(clienteEntity);

        EnderecoEntity enderecoCreated = enderecoRepository.save(entity);

        addLog("Endereço cadastrado com sucesso!");

        return ConverterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoCreated);
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        EnderecoEntity enderecoOpt = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        EnderecoEntity enderecoAtualizar = ConverterEnderecoParaDTOutil
                .converterByEndereco(enderecoCreateDTO);

        enderecoAtualizar.setIdEndereco(enderecoOpt.getIdEndereco());
        enderecoAtualizar.setCliente(enderecoOpt.getCliente());


        EnderecoEntity enderecoUpdated = enderecoRepository.save(enderecoAtualizar);

        addLog("Endereço atualizado com sucesso!");

        return ConverterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoUpdated);
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoOpt = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

            EnderecoEntity endereco = enderecoOpt;

            enderecoRepository.delete(endereco);

            addLog("Endereço deletado com sucesso!");
    }


}
