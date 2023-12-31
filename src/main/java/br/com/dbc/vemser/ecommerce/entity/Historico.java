package br.com.dbc.vemser.ecommerce.entity;

import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Document
public class Historico {
    @Id
    private String id;
    private Cargo cargo;
    private Setor setor;
    private String usuario;
    private String acao;
    private LocalDateTime dataAcao;
}
