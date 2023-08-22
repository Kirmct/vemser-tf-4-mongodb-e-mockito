package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("Select c From CLIENTE c where (:idCliente is null or c.idCliente = :idCliente)")
    List<ClienteEntity> buscarTodosOptionalId(Integer idCliente);

    boolean existsClienteEntitieByCpf(String cpf);

    boolean existsClienteEntitieByEmail(String email);

    boolean existsClienteEntitieByTelefone(String telefone);

    @Query("Select new br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO(c.idCliente, c.nome,c.telefone,c.email, c.cpf) " +
            "From CLIENTE c ")
    Page<ClientePaginadoDTO> buscarTodosClientesPaginados(Pageable pageable);

}
