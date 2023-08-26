package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoUpdateDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
    private final ConverterEnderecoParaDTOutil converterEnderecoParaDTOutil;
    private final HistoricoRepository historicoRepository;
    private final HistoricoBuilder historicoBuilder;
    private final UsuarioService usuarioService;

    public List<EnderecoDTO> listarEnderecos() throws RegraDeNegocioException {
        List<EnderecoEntity> enderecos = enderecoRepository.findAll();

        List<EnderecoDTO> enderecoDTOS = enderecos.stream()
                .map(converterEnderecoParaDTOutil::converterByEnderecoDTO).toList();


        Historico historico = historicoBuilder.inserirHistorico("Realizando listagem de endereços");
        historicoRepository.save(historico);

        return enderecoDTOS;
    }

    public EnderecoDTO getEnderecoById(Integer idEndereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoOpt = enderecoRepository
                .findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        return converterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoOpt);
    }

    public List<EnderecoDTO> listarEnderecoByIdCliente(Integer idCliente) throws Exception {
        List<EnderecoEntity> enderecos = enderecoRepository
                .findEnderecoEntityByCliente_IdCliente(idCliente);
        if (enderecos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum endereço encontrado para o cliente");
        }

        Historico historico = historicoBuilder.inserirHistorico("Realizando listagem de endereços por cliente");
        historicoRepository.save(historico);

        return enderecos.stream()
                .map(converterEnderecoParaDTOutil::converterByEnderecoDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO create(Integer idCliente, EnderecoCreateDTO enderecoCreateDTO) throws Exception {

        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(idCliente);


        if (clienteEntity.isEmpty()) {
            throw new RegraDeNegocioException("Cliente não encontrado");
        }

        EnderecoEntity entity = converterEnderecoParaDTOutil.converterByEndereco(enderecoCreateDTO);
        entity.setCliente(clienteEntity.get());

        EnderecoEntity enderecoCreated = enderecoRepository.save(entity);

        Historico historico = historicoBuilder.inserirHistorico("Endereço cadastrado com sucesso!");
        historicoRepository.save(historico);

        return converterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoCreated);
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) throws RegraDeNegocioException {
        EnderecoEntity enderecoOpt = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        EnderecoEntity enderecoAtualizar = converterEnderecoParaDTOutil
                .converterEndUpdateByEndereco(enderecoUpdateDTO);

        enderecoAtualizar.setIdEndereco(enderecoOpt.getIdEndereco());
        enderecoAtualizar.setCliente(enderecoOpt.getCliente());


        EnderecoEntity enderecoUpdated = enderecoRepository.save(enderecoAtualizar);

        Historico historico = historicoBuilder.inserirHistorico("Endereço atualizado com sucesso!");
        historicoRepository.save(historico);

        EnderecoDTO enderecoDTO = converterEnderecoParaDTOutil.converterByEnderecoDTO(enderecoUpdated);

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(idEndereco);
        if (enderecoOpt.isPresent()) {
            EnderecoEntity endereco = enderecoOpt.get();
            enderecoRepository.delete(endereco);

            Historico historico = historicoBuilder.inserirHistorico("Endereço deletado com sucesso!");
            historicoRepository.save(historico);
        }
    }


}
