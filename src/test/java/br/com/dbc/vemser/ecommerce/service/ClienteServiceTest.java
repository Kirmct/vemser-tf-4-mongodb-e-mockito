//package br.com.dbc.vemser.ecommerce.service;
//
//import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
//import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
//import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
//import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
//import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
//import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
//import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
//import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.ecommerce.exceptions.UniqueFieldExistsException;
//import br.com.dbc.vemser.ecommerce.repository.CargoRepository;
//import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
//import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
//import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ClienteServiceTest {
//
//    @InjectMocks
//    private ClienteService clienteService;
//
//    @Mock
//    private ClienteRepository clienteRepository;
//
//    @Mock
//    private CargoRepository cargoRepository;
//
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private PasswordEncoder bCript;
//
//    @Mock
//    private HistoricoBuilder historicoBuilder;
//
//    private UsuarioEntity usuario;
//
//    private ClienteCreateDTO clienteDTOCriado;
//
//    private ClienteEntity clienteMockado;
//
//    private ClienteDTO clienteDTO;
//
//    private CargoEntity cargo;
//
//    private Optional<ClienteEntity> clienteOptional;
//
//    private Optional<CargoEntity> cargoOptional;
//
//    private Optional<UsuarioEntity> usuarioOptional;
//
//    @BeforeEach
//    void setUp() {
//        clienteDTOCriado = new ClienteCreateDTO(
//                "Jeff",
//                "83223094837",
//                "email@teste.com",
//                "11122233344",
//                "123"
//        );
//
//        cargo = new CargoEntity(
//                2,
//                "ROLE_USUARIO",
//                null
//        );
//
//        usuario = new UsuarioEntity(2,
//                "email@teste.com",
//                bCript.encode(clienteDTOCriado.getSenha()),
//                null);
//
//        usuario.setCargos(Set.of(cargo));
//        cargo.setUsuarios(List.of(usuario));
//
//        clienteMockado = new ClienteEntity(
//                1,
//                "Jeff",
//                "83223094837",
//                "email@teste.com",
//                "11122233344",
//                new ArrayList<>(),
//                new ArrayList<>(),
//                usuario
//        );
//
//        clienteDTO = new ClienteDTO(
//                1,
//                2,
//                "Jeff",
//                "83223094837",
//                "email@teste.com",
//                "11122233344"
//        );
//
//        clienteOptional = Optional.of(clienteMockado);
//        cargoOptional = Optional.of(cargo);
//        usuarioOptional = Optional.of(usuario);
//
//        lenient().when(usuarioRepository.findById(anyInt())).thenReturn(usuarioOptional);
//    }
//
//    @Test
//    void save() throws RegraDeNegocioException, UniqueFieldExistsException {
//
//        when(clienteRepository.save(any())).thenReturn(clienteMockado);
//
//        when(cargoRepository.findById(2)).thenReturn(cargoOptional);
//
//        when(usuarioRepository.save(any())).thenReturn(usuario);
//
//        when(clienteRepository.save(any())).thenReturn(clienteMockado);
//
//        ClienteDTO clienteDTORetornado = clienteService.save(clienteDTOCriado);
//
//        Assertions.assertEquals(clienteDTORetornado.getIdCliente(), clienteDTO.getIdCliente());
//        Assertions.assertEquals(clienteDTORetornado.getTelefone(), clienteDTO.getTelefone());
//        Assertions.assertEquals(clienteDTORetornado.getEmail(), clienteDTO.getEmail());
//        Assertions.assertEquals(clienteDTORetornado.getCpf(), clienteDTO.getCpf());
//        Assertions.assertEquals(clienteDTORetornado.getNome(), clienteDTO.getNome());
//    }
//
//    @Test
//    void listarClientesComTodosOsDados() {
//        ClienteDadosCompletosDTO clienteDadosCompletosDTO = new ClienteDadosCompletosDTO(
//                1,
//                "Jeff",
//                "83223094837",
//                "email@teste.com",
//                "11122233344",
//                new ArrayList<>(),
//                new ArrayList<>()
//        );
//        List<ClienteDadosCompletosDTO> clientesCompletosDTO = List.of(clienteDadosCompletosDTO);
//
//        List<ClienteEntity> clienteEntities = List.of(clienteMockado);
//
//        when(clienteRepository.findAll()).thenReturn(clienteEntities);
//
//        List<ClienteDadosCompletosDTO> clientesCompletosRetornados = clienteService.listarClientesComTodosOsDados();
//
//        Assertions.assertEquals(clientesCompletosRetornados, clientesCompletosDTO);
//    }
//
//    @Test
//    void findAll() {
//        List<ClienteEntity> clientes = List.of(clienteMockado);
//        List<ClienteDTO> clientesDTO = List.of(clienteDTO);
//
//        when(clienteRepository.buscarTodosOptionalId(anyInt())).thenReturn(clientes);
//
//        List<ClienteDTO> clienteDTORetornado = clienteService.findAll(clienteMockado.getIdCliente());
//
//        Assertions.assertEquals(clienteDTORetornado.get(0).getIdCliente(), clientesDTO.get(0).getIdCliente());
//        Assertions.assertEquals(clienteDTORetornado.get(0).getIdUsuario(), clientesDTO.get(0).getIdUsuario());
//        Assertions.assertEquals(clienteDTORetornado.get(0).getNome(), clientesDTO.get(0).getNome());
//        Assertions.assertEquals(clienteDTORetornado.get(0).getCpf(), clientesDTO.get(0).getCpf());
//        Assertions.assertEquals(clienteDTORetornado.get(0).getTelefone(), clientesDTO.get(0).getTelefone());
//
//
//        when(clienteRepository.buscarTodosOptionalId(null)).thenReturn(clientes);
//        List<ClienteDTO> clientesDTORetornados = clienteService.findAll(null);
//
//        Assertions.assertEquals(clientesDTORetornados.get(0).getIdCliente(), clientesDTO.get(0).getIdCliente());
//        Assertions.assertEquals(clientesDTORetornados.get(0).getIdUsuario(), clientesDTO.get(0).getIdUsuario());
//        Assertions.assertEquals(clientesDTORetornados.get(0).getNome(), clientesDTO.get(0).getNome());
//        Assertions.assertEquals(clientesDTORetornados.get(0).getCpf(), clientesDTO.get(0).getCpf());
//        Assertions.assertEquals(clientesDTORetornados.get(0).getTelefone(), clientesDTO.get(0).getTelefone());
//    }
//
//    @Test
//    void clientePaginado() {
//        ClientePaginadoDTO clientePaginadoDTO = new ClientePaginadoDTO(
//                1,
//                "Jeff",
//                "83223094837",
//                "email@teste.com",
//                "11122233344"
//        );
//
//        Page<ClientePaginadoDTO> clienteEntitiesPage = new PageImpl<>(List.of(clientePaginadoDTO));
//
//        Pageable pageable = PageRequest.of(0, clienteEntitiesPage.getSize());
//
//        when(clienteRepository.buscarTodosClientesPaginados(pageable)).thenReturn(clienteEntitiesPage);
//
//        Page<ClientePaginadoDTO> clientePaginadoDTORetornado = clienteService.clientePaginado(pageable);
//        ClientePaginadoDTO clienteCobaiaTeste = clientePaginadoDTORetornado.getContent().get(0);
//
//        Assertions.assertEquals(clienteCobaiaTeste, clientePaginadoDTO);
//    }
//
//    @Test
//    void getByid() throws RegraDeNegocioException {
//
//        when(clienteRepository.findById(anyInt())).thenReturn(clienteOptional);
//
//        ClienteDTO clienteDTORetornado = clienteService.getByid(clienteMockado.getIdCliente());
//
//        Assertions.assertEquals(clienteDTORetornado.getIdCliente(), clienteDTO.getIdCliente());
//        Assertions.assertEquals(clienteDTORetornado.getIdUsuario(), clienteDTO.getIdUsuario());
//        Assertions.assertEquals(clienteDTORetornado.getNome(), clienteDTO.getNome());
//        Assertions.assertEquals(clienteDTORetornado.getCpf(), clienteDTO.getCpf());
//        Assertions.assertEquals(clienteDTORetornado.getEmail(), clienteDTO.getEmail());
//        Assertions.assertEquals(clienteDTORetornado.getTelefone(), clienteDTO.getTelefone());
//    }
//
//    @Test
//    void update() throws RegraDeNegocioException {
//        ClienteCreateDTO clienteAlterado = new ClienteCreateDTO(
//                "Jeff teste",
//                "83245094737",
//                "email2@teste.com",
//                "21211135374",
//                "123"
//        );
//
//        ClienteEntity clienteAlteradoRetorno = new ClienteEntity(
//                1,
//                "Jeff teste",
//                "83245094737",
//                "email2@teste.com",
//                "21211135374",
//                new ArrayList<>(),
//                new ArrayList<>(),
//                usuario
//        );
//
//        ClienteDTO clienteDTOModeloDeRetorno = new ClienteDTO(
//                1,
//                2,
//                "Jeff teste",
//                "83245094737",
//                "email2@teste.com",
//                "21211135374"
//        );
//
//        when(clienteRepository.findById(anyInt())).thenReturn(clienteOptional);
//
//        when(clienteRepository.save(any())).thenReturn(clienteAlteradoRetorno);
//
//        ClienteDTO clienteRetornadoDoTeste = clienteService.update(clienteMockado.getIdCliente(), clienteAlterado);
//
//        Assertions.assertEquals(clienteRetornadoDoTeste.getIdCliente(), clienteDTOModeloDeRetorno.getIdCliente());
//        Assertions.assertEquals(clienteRetornadoDoTeste.getIdUsuario(), clienteDTOModeloDeRetorno.getIdUsuario());
//        Assertions.assertEquals(clienteRetornadoDoTeste.getNome(), clienteDTOModeloDeRetorno.getNome());
//        Assertions.assertEquals(clienteRetornadoDoTeste.getCpf(), clienteDTOModeloDeRetorno.getCpf());
//        Assertions.assertEquals(clienteRetornadoDoTeste.getEmail(), clienteDTOModeloDeRetorno.getEmail());
//        Assertions.assertEquals(clienteRetornadoDoTeste.getTelefone(), clienteDTOModeloDeRetorno.getTelefone());
//    }
//
//    @Test
//    void delete() {
//        when(clienteRepository.getById(anyInt())).thenReturn(clienteMockado);
//
//        clienteService.delete(clienteMockado.getIdCliente());
//        verify(clienteRepository, times(1)).delete(eq(clienteMockado));
//    }
//
//    @Test
//    void findById() throws RegraDeNegocioException {
//
//        when(clienteRepository.findById(anyInt())).thenReturn(clienteOptional);
//
//        ClienteEntity clienteBuscado = clienteService.findById(1);
//
//        assertEquals(clienteMockado, clienteBuscado);
//    }
//}