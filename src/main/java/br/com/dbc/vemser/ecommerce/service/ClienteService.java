package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.exceptions.UniqueFieldExistsException;
import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder bCript;
    private final HistoricoBuilder historicoBuilder;

    @SneakyThrows
    private void addLog(String mensagem) {
        historicoBuilder.inserirHistorico(mensagem, Setor.CLIENTE);
    }

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

    public ClienteDTO save(ClienteCreateDTO clienteCreateDTO) throws UniqueFieldExistsException, RegraDeNegocioException {
        Map<String, String> campo = validarNovoCliente(clienteCreateDTO);

        if (campo.size() != 0) {
            throw new UniqueFieldExistsException(campo);
        }

        UsuarioEntity user = new UsuarioEntity();
        String senhaCript = bCript.encode(clienteCreateDTO.getSenha());

        user.setSenha(senhaCript);
        user.setLogin(clienteCreateDTO.getEmail());

        Optional<CargoEntity> userCargo = cargoRepository.findById(2);

        if (userCargo.isEmpty()){
            throw new RegraDeNegocioException("Cargo não existe");
        }

        user.getCargos().add(userCargo.get());
        UsuarioEntity novoUser = usuarioRepository.save(user);

        ClienteEntity cliente = ConversorMapper.converter(clienteCreateDTO, ClienteEntity.class);
        cliente.setUsuario(novoUser);

        ClienteDTO clienteDTO = ConversorMapper.converter(clienteRepository.save((cliente)), ClienteDTO.class);

        clienteDTO.setIdUsuario(novoUser.getIdUsuario());

        addLog("CADASTROU UM CLIENTE.");

        return clienteDTO;
    }

    public List<ClienteDadosCompletosDTO> listarClientesComTodosOsDados() {
        addLog("Listou os clientes com todos os dados.");

        return clienteRepository.findAll()
                .stream().map(cliente -> {
                    ClienteDadosCompletosDTO clienteConvertido = ConversorMapper.converter(cliente, ClienteDadosCompletosDTO.class);

                    clienteConvertido.setPedidoEntities(cliente.getPedidoEntities()
                            .stream()
                            .map(pedido -> ConversorMapper.converter(pedido, PedidoDTO.class)).toList());

                    clienteConvertido.setEnderecoEntities(cliente.getEnderecoEntities().stream().map(endereco -> {
                        EnderecoDTO enderecoConvertdo = ConversorMapper.converter(endereco, EnderecoDTO.class);
                        enderecoConvertdo.setIdCliente(endereco.getCliente().getIdCliente());
                        return enderecoConvertdo;
                    }).toList());

                    return clienteConvertido;
                }).toList();
    }

    public List<ClienteDTO> findAll(Integer idCliente) {
        List<ClienteDTO> clientesConvertidos = clienteRepository.buscarTodosOptionalId(idCliente)
                .stream()
                .map(cliente -> {
                    ClienteDTO clienteDTO = ConversorMapper.converter(cliente, ClienteDTO.class);
                    clienteDTO.setIdUsuario(cliente.getUsuario().getIdUsuario());
                    return clienteDTO;
                })
                .collect(Collectors.toList());

        addLog("Listou todos os clientes.");
        return clientesConvertidos;
    }

    public Page<ClientePaginadoDTO> clientePaginado(Pageable pageable) {
        addLog("Listou os clientes paginadamente.");
        return clienteRepository.buscarTodosClientesPaginados(pageable);
    }

    public ClienteDTO getByid(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteBuscado = findById(idCliente);
        ClienteDTO clienteDTO = ConversorMapper.converter(clienteBuscado, ClienteDTO.class);
        clienteDTO.setIdUsuario(clienteBuscado.getUsuario().getIdUsuario());

        addLog("Buscou um cliente pelo ID.");
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        ClienteEntity findedClient = findById(idCliente);
        findedClient.setCpf(clienteCreateDTO.getCpf());
        findedClient.setNome(clienteCreateDTO.getNome());
        findedClient.setTelefone(clienteCreateDTO.getTelefone());
        findedClient.setEmail(clienteCreateDTO.getEmail());

        findedClient = clienteRepository.save(findedClient);
        ClienteDTO updatedClient = ConversorMapper.converter(findedClient, ClienteDTO.class);
        updatedClient.setIdUsuario(findedClient.getUsuario().getIdUsuario());

        addLog("Fez um update em " + findedClient.getNome() + ".");
        return updatedClient;
    }

    public void delete(Integer idCliente) {
        addLog("Deletou um cliente.");
        ClienteEntity clienteEntity = clienteRepository.getById(idCliente);
        clienteRepository.delete(clienteEntity);
    }

    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException {
        addLog("Buscou um cliente pelo ID.");
        return clienteRepository.findById(idcliente).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

}
