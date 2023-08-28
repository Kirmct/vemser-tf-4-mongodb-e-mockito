package br.com.dbc.vemser.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "financeiro")
public class FinanceiroEntity {

    @Id
    private String id;

    private Double total;

    private LocalDate dataDePagamento;

    private Integer idPedido;

}
