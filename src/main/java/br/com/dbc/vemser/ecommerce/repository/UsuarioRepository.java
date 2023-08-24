package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String login);

    @Query(nativeQuery = true, value = """
        SELECT c.nome
        FROM USUARIO_CARGO uc
        JOIN CARGO c ON uc.id_cargo = c.id_cargo
        WHERE uc.id_usuario = :idUsuario     
    """)
    String findByRole(@Param("idUsuario") Integer idUsuario);
}