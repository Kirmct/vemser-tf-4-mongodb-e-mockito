package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.ConverterPedidoParaDTOutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder bCript;

    private final ConverterEnderecoParaDTOutil converterEnderecoParaDTOutil;
    private final ConverterPedidoParaDTOutil converterPedidoParaDTOutil;

    public Map<String, String> validarNovoCliente(ClienteCreateDTO clienteCreateDTO) {
        Map<String, String> existe = new HashMap<>();
        if (clienteRepository.existsClienteEntitieByEmail(clienteCreateDTO.getEmail())) {
            existe.put("email", "já cadastrado");
        }
        if (clienteRepository.existsClienteEntitieByCpf(clienteCreateDTO.getCpf())) {
            existe.put("cpf", "já cadastrado");
        }
        if (clienteRepository.existsClienteEntitieByTelefone(clienteCreateDTO.getTelefone())) {
            existe.put("telefone", "já cadastrado");
        }

        return existe;
    }

    public ClienteDTO save(ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        Map<String, String> campo = validarNovoCliente(clienteCreateDTO);

        if (campo.size() != 0) {
            throw new RegraDeNegocioException(campo);
        }

        UsuarioEntity user = new UsuarioEntity();
        String senhaCript = bCript.encode(clienteCreateDTO.getSenha());

        user.setSenha(senhaCript);
        user.setLogin(clienteCreateDTO.getEmail());

        Optional<CargoEntity> userCargo = cargoRepository.findByNome("ROLE_USUARIO");

        if (userCargo.isEmpty()){
            throw new RegraDeNegocioException("Cargo não existe");
        }

        user.getCargos().add(userCargo.get());
        UsuarioEntity novoUser = usuarioRepository.save(user);

        ClienteEntity cliente = objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
        cliente.setUsuario(novoUser);

        ClienteDTO clienteDTO = convertToDto(clienteRepository.save((cliente)));

        clienteDTO.setIdUsuario(novoUser.getIdUsuario());

        return clienteDTO;
    }

    public List<ClienteDadosCompletosDTO> listarClientesComTodosOsDados() {

        return clienteRepository.findAll()
                .stream().map(this::converterClienteParaDTO).toList();

    }

    public List<ClienteDTO> findAll(Integer idCliente) {
        return clienteRepository.buscarTodosOptionalId(idCliente)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<ClientePaginadoDTO> clientePaginado(Pageable pageable) {

        return clienteRepository.buscarTodosClientesPaginados(pageable);
    }

    public ClienteDTO getByid(Integer idCliente) throws RegraDeNegocioException {
        ClienteDTO clienteDTO = convertToDto(findById(idCliente));
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        ClienteEntity findedClient = findById(idCliente);
        findedClient.setCpf(clienteCreateDTO.getCpf());
        findedClient.setNome(clienteCreateDTO.getNome());
        findedClient.setTelefone(clienteCreateDTO.getTelefone());
        findedClient.setEmail(clienteCreateDTO.getEmail());
        ClienteDTO updatedClient = convertToDto(clienteRepository.save(findedClient));
        return updatedClient;
    }

    public void delete(Integer idCliente) {
        ClienteEntity clienteEntity = clienteRepository.getById(idCliente);
        clienteRepository.delete(clienteEntity);

    }


    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException {
        return clienteRepository.findById(idcliente).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteDTO convertToDto(ClienteEntity clienteEntity) {
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteEntity, ClienteDTO.class);
        clienteDTO.setIdUsuario(clienteEntity.getUsuario().getIdUsuario());
        return clienteDTO;
    }

    public ClienteEntity convertToEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    private ClienteDadosCompletosDTO converterClienteParaDTO(ClienteEntity clienteEntity) {

        Set<EnderecoDTO> enderecoDTO = clienteEntity.getEnderecoEntities()
                .stream().map(converterEnderecoParaDTOutil::converterByEnderecoDTO)
                .collect(Collectors.toSet());

        Set<PedidoDTO> pedidoDTO = clienteEntity.getPedidoEntities()
                .stream().map(c -> converterPedidoParaDTOutil.converterPedidooParaDTO(c))
                .collect(Collectors.toSet());


        ClienteDadosCompletosDTO clienteDadosCompletosDTO =
                objectMapper.convertValue(clienteEntity, ClienteDadosCompletosDTO.class);

        clienteDadosCompletosDTO.setEnderecoEntities(enderecoDTO);
        clienteDadosCompletosDTO.setPedidoEntities(pedidoDTO);

        return clienteDadosCompletosDTO;
    }
}