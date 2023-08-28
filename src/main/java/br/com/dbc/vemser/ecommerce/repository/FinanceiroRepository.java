package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoContadorDTO;
import br.com.dbc.vemser.ecommerce.entity.FinanceiroEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface FinanceiroRepository extends MongoRepository<FinanceiroEntity, String> {


}
