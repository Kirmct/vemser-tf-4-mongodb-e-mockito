package br.com.dbc.vemser.ecommerce.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
public class Historico {
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String usuario;
    private String acao;
    private LocalDateTime dataAcao;
}
