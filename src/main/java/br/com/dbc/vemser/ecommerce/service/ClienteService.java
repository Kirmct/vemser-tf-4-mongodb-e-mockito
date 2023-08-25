package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.dto.usuario.UsuarioLogadoDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.UniqueFieldExistsException;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.ConverterPedidoParaDTOutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder bCript;
    private final HistoricoRepository historicoRepository;

    private final ConverterEnderecoParaDTOutil converterEnderecoParaDTOutil;
    private final ConverterPedidoParaDTOutil converterPedidoParaDTOutil;

    @SneakyThrows
    private UsuarioEntity getUsuarioByToken() {
        return usuarioRepository.findById(usuarioService.getIdLoggedUser()).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    }

    private void addLog(UsuarioEntity usuario, String acao) {
        Historico historico = new Historico();
        historico.setUsuario(usuario.getLogin());
        historico.setCargo(Cargo.valueOf(getUsuarioByToken().getCargos().stream().findFirst().orElseThrow().getNome()));
        historico.setAcao(acao);
        historico.setDataAcao(LocalDateTime.now());

        historicoRepository.save(historico);
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
//        Map<String, String> campo = validarNovoCliente(clienteCreateDTO);
//
//        if (campo.size() != 0) {
//            throw new UniqueFieldExistsException(campo);
//        }

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
//
        ClienteDTO clienteDTO =  ConversorMapper.converter(clienteRepository.save((cliente)), ClienteDTO.class);

        clienteDTO.setIdUsuario(novoUser.getIdUsuario());

        addLog(user, "CADASTROU UM CLIENTE.");

        return clienteDTO;
    }

    public List<ClienteDadosCompletosDTO> listarClientesComTodosOsDados() throws RegraDeNegocioException {
        addLog(getUsuarioByToken(), "Listou os clientes com todos os dados.");
        return clienteRepository.findAll()
                .stream().map(this::converterClienteParaDTO).toList();

    }

    public List<ClienteDTO> findAll(Integer idCliente) {
        addLog(getUsuarioByToken(), "Listou todos os clientes.");
        return clienteRepository.buscarTodosOptionalId(idCliente)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<ClientePaginadoDTO> clientePaginado(Pageable pageable) {
        addLog(getUsuarioByToken(), "Listou os clientes paginadamente.");
        return clienteRepository.buscarTodosClientesPaginados(pageable);
    }

    public ClienteDTO getByid(Integer idCliente) throws RegraDeNegocioException {
        ClienteDTO clienteDTO = convertToDto(findById(idCliente));
        addLog(getUsuarioByToken(), "Buscou um cliente pelo ID.");
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        ClienteEntity findedClient = findById(idCliente);
        findedClient.setCpf(clienteCreateDTO.getCpf());
        findedClient.setNome(clienteCreateDTO.getNome());
        findedClient.setTelefone(clienteCreateDTO.getTelefone());
        findedClient.setEmail(clienteCreateDTO.getEmail());
        ClienteDTO updatedClient = convertToDto(clienteRepository.save(findedClient));

        addLog(getUsuarioByToken(), "Fez um update em " + findedClient.getNome() + ".");
        return updatedClient;
    }

    public void delete(Integer idCliente) {
        addLog(getUsuarioByToken(), "Deletou um cliente.");
        ClienteEntity clienteEntity = clienteRepository.getById(idCliente);
        clienteRepository.delete(clienteEntity);
    }

    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException {
        addLog(getUsuarioByToken(), "Buscou um cliente pelo ID.");
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