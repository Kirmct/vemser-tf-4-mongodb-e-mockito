package br.com.dbc.vemser.ecommerce.dto.financeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDate;

@Data
public class FinanceiroDTO {


    private String id;

    private Double total;

    private LocalDate dataDePagamento;

    private Integer idPedido;
}
